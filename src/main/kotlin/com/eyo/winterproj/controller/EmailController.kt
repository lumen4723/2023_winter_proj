package com.eyo.winterproj.controller

import com.eyo.winterproj.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/mailCheck")
class EmailController(@Autowired val emailService: EmailService) {
    @GetMapping("/")
    @ResponseBody
    fun send(user: emailRequestEntity) : String {
        val result =  emailService.sendEmail(user.email)
        println(result)
        return "TEST";
    }
}

data class emailRequestEntity(
    val email:String,
)
