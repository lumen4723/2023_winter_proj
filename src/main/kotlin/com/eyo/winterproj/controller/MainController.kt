package com.eyo.winterproj.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model
import jakarta.servlet.http.HttpSession

@Controller
@RequestMapping("/")
class MainController {
    @GetMapping("/")
    fun main(model: Model, session: HttpSession): String {
        val user = session.getAttribute("user")
        if (user != null) {
            model.addAttribute("user", user)
        }
        return "index"
    }

    @GetMapping("/notice")
    fun notice(): String {
        return "notice"
    }
}
