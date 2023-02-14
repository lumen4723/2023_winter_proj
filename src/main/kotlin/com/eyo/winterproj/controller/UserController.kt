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
        if(user.isPresent){
            session.setAttribute("user", user.get())
            return "redirect:/"
        }
        return "redirect:/user/login?msg=LoginFailed"
    }
    @PostMapping("/logout")
    fun logout(user: LogoutRequestEntity, session: HttpSession): String{
            session.removeAttribute("user")
//            session.invalidate()
            return "redirect:/"
    }

    @GetMapping("/signup")
    fun register():String{
        return "signup"
    }

    @PostMapping("/signup")
    fun registerP(user:RegisterRequestEntity) :String{
        userService.register(user.email, user.password, user.username)
        return "redirect:/user/login"
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