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
    fun main(model: Model): String {
        return "index"
    }

    @GetMapping("/notice")
    fun notice(): String {
        return "notice"
    }
}
