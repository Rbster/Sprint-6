package ru.sber.mvcExample.filters

import org.springframework.context.annotation.ComponentScan
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

@Component
@Order(1)
class LogFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        println("-----> Filtering in LogFilter")
        chain!!.doFilter(request, response)
    }

}

@Component
@Order(2)
class CheckAuthFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        println("-----> Filtering in CheckAuthFilter")
        chain!!.doFilter(request, response)
    }

}