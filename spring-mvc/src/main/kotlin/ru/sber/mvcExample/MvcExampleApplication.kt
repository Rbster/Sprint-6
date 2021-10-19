package ru.sber.mvcExample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MvcExampleApplication

fun main(args: Array<String>) {
	runApplication<MvcExampleApplication>(*args)
}
