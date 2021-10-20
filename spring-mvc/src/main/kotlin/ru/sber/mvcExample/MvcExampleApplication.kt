package ru.sber.mvcExample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import ru.sber.mvcExample.servlet.MainServlet
import java.time.Clock

@SpringBootApplication
@EnableWebMvc
class MvcExampleApplication {
	@Bean
	fun mainServletBean(): ServletRegistrationBean<*> {
		val bean: ServletRegistrationBean<*> = ServletRegistrationBean(
			MainServlet(), "/exampleServlet/*"
		)
		bean.setLoadOnStartup(1)
		println("----->" + bean.servletName)
		return bean
	}
	@Bean
	fun clock(): Clock {
		return Clock.systemDefaultZone()
	}
}

fun main(args: Array<String>) {
	runApplication<MvcExampleApplication>(*args)
}