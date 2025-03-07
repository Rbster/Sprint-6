import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.lang.RuntimeException
import java.net.ServerSocket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param socket Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            val clientSocket = socket.accept()
            clientSocket.use { s ->
                // read request
                val reader = s.getInputStream().bufferedReader()
                val clientRequest = reader.readLine()
                // process request
                val requestParts = clientRequest?.split(' ')
                val vPath: VPath?
                var content: String? = null
                if (requestParts != null &&
                    requestParts.size == 3 &&
                    (requestParts[2] == "HTTP/1.1" ||
                            requestParts[2] == "HTTP/1.0" ||
                            requestParts[2] == "HTTP/2") &&
                    requestParts[0] == "GET"
                ) {

                    vPath = getVPathOrNull(requestParts[1])
                    content = if (vPath != null) fs.readFile(vPath) else null
                }

                // send response
                val writer = s.getOutputStream().bufferedWriter()
                writer.write(responseString(content))
                writer.flush()
            }

        }

    }

    private fun responseString(content: String?): String {
        return if (content == null) {
            "HTTP/1.0 404 Not Found\r\n" +
            "Server: FileServer\r\n" +
            "\r\n"
        } else {
            "HTTP/1.0 200 OK\r\n" +
            "Server: FileServer\r\n" +
            "\r\n$content\r\n"
        }
    }

    private fun getVPathOrNull(path: String): VPath? =
        try {
            VPath(path)
        } catch (e: RuntimeException) {
            null
        }

}



