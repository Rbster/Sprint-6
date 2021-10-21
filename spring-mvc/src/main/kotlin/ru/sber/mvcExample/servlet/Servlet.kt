package ru.sber.mvcExample.servlet

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.nio.file.Paths
import java.time.Clock
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.servlet.ServletContext
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.io.path.inputStream
import kotlin.streams.asSequence

class LoginServlet(@Autowired val clock: Clock) : HttpServlet() {

//    lateinit var clock: Clock
//    @Autowired set

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        var cookie: Cookie? = null

        if (req.cookies != null) {
            for (q in req.cookies) {
                if (q.name == "auth") {
                    cookie = q
                }
            }
        }

        println("-------------->" + clock.instant().toString())

        if (cookie == null ||
                    Instant.from(
                        DateTimeFormatter.ISO_INSTANT.parse(cookie.value)
                    ) >= clock.instant() ) {
            resp.contentType = "text/html"
            val outputStream = resp.outputStream
//            println(Paths.get("").toAbsolutePath())

            Paths.get("spring-mvc/src/main/resources/templates/login.html").inputStream().use { inputStream ->
                inputStream.transferTo(outputStream)
            }
            outputStream.flush()
            outputStream.close() // should I?
//            req.getRequestDispatcher("login.html").forward(req, resp)

        } else {
            resp.sendRedirect("/app/list")
        }
    }


    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val login = "admin"
        val password = "admin"
        val body = req.reader.lines().asSequence().filterNotNull().joinToString("").filter { !it.isWhitespace() }

        var cookie: Cookie? = null

        println("------------>${req.attributeNames.toList()}")
        println("------------>${req.reader.lines().asSequence().filterNotNull().joinToString(System.lineSeparator())}")
        println("------------>${req.getHeader("Content-Type")}")

        if (req.cookies != null) {
            for (q in req.cookies) {
                if (q.name == "auth") {
                    cookie = q
                }
            }
        }

        println("-------------->" + clock.instant().toString())

        if (cookie == null ||
            Instant.from(
                DateTimeFormatter.ISO_INSTANT.parse(cookie.value)
            ) >= clock.instant() ) {

            if ((req.getHeader("Content-Type") == "application/x-www-form-urlencoded" )
                && (body == "log=${login}&password=${password}")
                || (req.getHeader("Content-Type") == "application/json")
                && (body == "{log:\"${login}\",password:\"${password}\"}")
            ) {
                println("----------> LOG IN")
//                resp.addCookie(Cookie("auth", clock.instant().toString()))
                resp.sendRedirect("/alt/login/form")

            } else {
                resp.sendRedirect("/alt/login/form")
            }

        } else {
            resp.sendRedirect("/app/list")
        }
    }
}