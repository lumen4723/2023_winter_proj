package com.eyo.winterproj.controller

import com.eyo.winterproj.service.EmailService
import com.eyo.winterproj.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("user")
class UserController(@Autowired val userService: UserService, @Autowired val emailService: EmailService) {
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
    fun logout(request: HttpServletRequest, session: HttpSession): String{
        val session = request.getSession(false)
        if (session != null) {
            session.invalidate()
            return "redirect:/"
        }
        return "redirect:/user/logout?msg=LogoutFailed"
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
    @GetMapping("/mailCheck")
    @ResponseBody
    fun send(user: emailRequestEntity) : String {
        val result =  emailService.sendEmail(user.email)
        return "TEST";
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
