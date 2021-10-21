package ru.sber.mvcExample.controllers

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.mvcExample.repository.AddressBookRepository
import ru.sber.mvcExample.repository.AddressInfo
import ru.sber.mvcExample.repository.AddressSearchForm
import java.time.Clock
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class MVCController {
    lateinit var addressBookRepository: AddressBookRepository
    @Autowired set

    lateinit var clock: Clock
    @Autowired set

    @PostMapping("/auth")
    fun authorising(@ModelAttribute loginForm: LoginFormModel, request: HttpServletRequest, response: HttpServletResponse) {
        println("LoginForm = $loginForm")
        if (loginForm.log == "admin" && loginForm.password == "admin") {
            response.addCookie(Cookie("auth", clock.instant().toString()))
        }
        response.sendRedirect(request.contextPath + "/app/list")
    }

    @GetMapping("/login")
    fun login(model: Model): String {
        return "login"
    }

    @PostMapping("/app/add")
    fun add(@ModelAttribute element: AddressInfo): String {
        println("---------->$element")
        val id: String? = addressBookRepository.add(element)
        if (id == null) {
            logger.error("Add went wrong. Element $element wasn't added")
        } else {
            logger.info("Element with $id added")
        }
        return "redirect:/app/list"
    }
    @GetMapping("/app/list")
    fun list(@ModelAttribute searchForm: AddressSearchForm, model: Model): String {
        val foundEntries = addressBookRepository.list(searchForm)
        model.addAttribute("list_of_elements", foundEntries.values.toList())
        logger.info("Listed ${foundEntries.size} elements")
        return "app/list"
    }
    @GetMapping("/app/{id}/view")
    fun viewById(@PathVariable("id") id: String, model: Model): String {
        val element = addressBookRepository.view(id)
        if (element == null) {
            logger.error("No element with id = $id")
        } else {
            logger.info("Viewing element with id = $id")
        }

        model.addAttribute("id", element)
        return "viewById"
    }
    @PatchMapping("/app/{id}/edit")
    fun editById(@PathVariable("id") id: String, @ModelAttribute searchForm: AddressSearchForm, model: Model): String {
        val element = addressBookRepository.view(id)
        if (element == null) {
            logger.error("No element with id = $id, can't modify")
        } else {
            val isEdited = addressBookRepository.edit(id, searchForm)
            if (isEdited) {
                logger.info("Element $id edited all right")
            } else {
                logger.error("Editing error!")
            }
        }

        return "editById"
    }
    @DeleteMapping("/app/{id}/delete")
    fun deleteById(@PathVariable("id") id: String): String {
        if (addressBookRepository.delete(id)) {
            logger.info("Element $id was deleted")
        } else {
            logger.error("Error during deletion $id !")
        }
        return "deleteById"
    }

    data class LoginFormModel(val log: String, val password: String)
    companion object {
        var logger: Log = LogFactory.getLog(this::class.java)
    }
}