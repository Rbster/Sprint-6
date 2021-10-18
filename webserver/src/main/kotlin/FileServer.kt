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

            // TODO Delete this once you start working on your solution.
            //throw new UnsupportedOperationException();

            // TODO 1) Use socket.accept to get a Socket object
            var clientSocket = socket.accept()

            /*
            * TODO 2) Using Socket.getInputStream(), parse the received HTTP
            * packet. In particular, we are interested in confirming this
            * message is a GET and parsing out the path to the file we are
            * GETing. Recall that for GET HTTP packets, the first line of the
            * received packet will look something like:
            *
            *     GET /path/to/file HTTP/1.1
            */
            var requestString: String?
            var requestParts: List<String>?
            var vPath: VPath?
            var content: String? = null
            var clientInputStream = clientSocket.getInputStream()

            clientInputStream.use {
                it.bufferedReader().use {
                    requestString = it.readLine()
                }
            }



            requestParts = requestString?.split(' ')
            if (requestParts != null &&
                requestParts.size == 3 &&
                (requestParts[2] == "HTTP/1.1" ||
                requestParts[2] == "HTTP/1.0" ||
                requestParts[2] == "HTTP/2") &&
                requestParts[0] == "GET") {

                vPath = getVPathOrNull(requestParts[1])
                content = if (vPath != null) fs.readFile(vPath) else null

            }



            var clientOutputStream = clientSocket.getOutputStream()
            clientOutputStream.use {
                it.bufferedWriter().use {
                    it.write(responseString(content))
                }
            }


            clientSocket.close()


            /*
             * TODO 3) Using the parsed path to the target file, construct an
             * HTTP reply and write it to Socket.getOutputStream(). If the file
             * exists, the HTTP reply should be formatted as follows:
             *
             *   HTTP/1.0 200 OK\r\n
             *   Server: FileServer\r\n
             *   \r\n
             *   FILE CONTENTS HERE\r\n
             *
             * If the specified file does not exist, you should return a reply
             * with an error code 404 Not Found. This reply should be formatted
             * as:
             *
             *   HTTP/1.0 404 Not Found\r\n
             *   Server: FileServer\r\n
             *   \r\n
             *
             * Don't forget to close the output stream.
             */
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



