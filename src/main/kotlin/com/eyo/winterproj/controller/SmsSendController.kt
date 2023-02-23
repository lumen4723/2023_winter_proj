package com.eyo.winterproj.controller

import com.eyo.winterproj.service.SmsSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/sms")
class SmsSendController(@Autowired val smsSvc : SmsSenderService) {
    @GetMapping("/send")
    @ResponseBody
    fun sendSMS() : String{
        smsSvc.sendSMS()

        return "<script>alert('ok');</script>";
    }
}