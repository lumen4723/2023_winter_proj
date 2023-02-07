package com.eyo.winterproj.controller

import org.springframework.stereotype.Controller


@Controller
class UserController {

}

data class RegisterRequestEntity(
    val email:String,
    val password:String,
    val username:String,
)