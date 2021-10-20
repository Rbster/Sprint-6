package ru.sber.mvcExample.filters

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.CommonsRequestLoggingFilter
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@Order(1)
class LoggingFilter : Filter {
    companion object {
        var logger: Log = LogFactory.getLog(CommonsRequestLoggingFilter::class.java)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        println("-----> Filtering in CheckAuthFilter")
        val httpRequest = request as HttpServletRequest
        logger.info(httpRequest.method + " request from " + httpRequest.remoteAddr)
        chain!!.doFilter(request, response)
        println("<----- out CheckAuthFilter")
    }
}

@Component
@Order(2)
class AuthFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        println("-----> Filtering in LogFilter")
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        var cookie: Cookie? = null
        val loginURI = request.contextPath + "/login"
        val requestURI = request.requestURI

        if (httpRequest.cookies != null) {
            for (q in httpRequest.cookies) {
                if (q.name == "authorised") {
                    cookie = q
                }
            }
        }

        if (requestURI != loginURI && (cookie == null || cookie.name != "something")) {
            // redirect
            println("<----- redirect from LogFilter")
            httpResponse.sendRedirect(loginURI)

        } else {
            chain!!.doFilter(request, response)
        }
        println("<----- out LogFilter")
    }

}

