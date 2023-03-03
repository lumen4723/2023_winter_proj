package com.eyo.winterproj.controller

import com.eyo.winterproj.entity.UserEntity
import com.eyo.winterproj.service.EmailService
import com.eyo.winterproj.service.SmsSenderService
import com.eyo.winterproj.service.UserService
import com.eyo.winterproj.util.HashUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime


@Controller
@RequestMapping("user")
class UserController(@Autowired val userService: UserService, @Autowired val emailService: EmailService,@Autowired val smsSvc : SmsSenderService) {
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
        if(user.validnumber==HashUtil().getHash(user.number).substring(0..6)){
            userService.register(user.email, user.password, user.username, user.number)
            return "redirect:/user/login"
        }
        else return "redirect:/user/signup?msg=%EC%9D%B8%EC%A6%9D%EC%97%90%20%EC%8B%A4%ED%8C%A8%ED%96%88%EC%8A%B5%EB%8B%88%EB%8B%A4"
    }

    @GetMapping("/sendSMS")
    @ResponseBody
    fun sendSMS(number:String) : String {
        if (number.length < 5) {
            return "올바르지 않은 전화번호";
        }
        smsSvc.sendSMS(number, HashUtil().getHash(number).substring(0..6))
        return "ok";
    }

    @GetMapping("/validNum")
    @ResponseBody
    fun validNum(number: String, validnumber:String) : String{
        if( validnumber ==HashUtil().getHash(number).substring(0..6)){
            return "인증이 완료되었습니다";
        }
        return "올바르지 않은 인증번호";
    }

    @GetMapping("/mailCheck")
    @ResponseBody
    fun send(email:String) : String {
//        val random = (0..999999).random().toString().padStart(6, '0')
//        emailService.sendEmail(user.email, random)
//        emailService.checkCode(email, random, LocalDateTime.now())
        emailService.sendEmail(email, HashUtil().getHash(email).substring(0..6))
        return "ok";
    }
    @GetMapping("/authEmail")
    @ResponseBody
    fun authEmail(email: String, authCode:String) : String{
        if( authCode ==HashUtil().getHash(email).substring(0..6)){
            return "인증이 완료되었습니다";
        }
        return "올바르지 않은 인증번호입니다.\n다시 입력해주세요.";
    }
}

data class RegisterRequestEntity(
    val email:String,
    val password:String,
    val username:String,
    val number:String,
    val validnumber: String
)
data class LoginRequestEntity(
    val email:String,
    val password:String
)
//data class EmailRequestEntity(
//    val email:String
//)