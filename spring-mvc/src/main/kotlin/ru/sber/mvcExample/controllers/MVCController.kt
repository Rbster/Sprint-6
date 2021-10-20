package ru.sber.mvcExample.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class MVCController {

    @GetMapping()
    fun login(): String {
        return "login"
    }
    @PutMapping("/app/add")
    fun add(): String {
        return "add"
    }
    @GetMapping("/app/list")
    fun list(): String {
        return "list"
    }
    @GetMapping("/app/{id}/view")
    fun viewById(@PathVariable("id") id: String, model: Model): String {
        return "viewById"
    }
    @PatchMapping("/app/{id}/view")
    fun editById(@PathVariable("id") id: String, model: Model): String {
        return "editById"
    }
    @DeleteMapping("/app/{id}/delete")
    fun deleteById(@PathVariable("id") id: String, model: Model): String {

        return "deleteById"
    }
}