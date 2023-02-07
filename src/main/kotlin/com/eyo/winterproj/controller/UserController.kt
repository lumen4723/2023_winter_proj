package com.eyo.winterproj.controller

import com.eyo.winterproj.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("user")
class UserController(@Autowired val userService: UserService) {
    @GetMapping("/login")
    fun login():String{
        return "login"
    }
    @PostMapping("/login")
    fun loginP(user: LoginRequestEntity, session: HttpSession):String{
        val user = userService.login(user.email, user.password)
        return "redirect:/"
    }
    @PostMapping("/logout")
    fun logout(user: LogoutRequestEntity, session: HttpSession): String{
        return "redirect:/"
    }
}

data class RegisterRequestEntity(
    val email:String,
    val password:String,
    val username:String,
)
data class LoginRequestEntity(
    val email:String,
    val password:String
)
data class LogoutRequestEntity(
    val email: String,
    val password: String
)