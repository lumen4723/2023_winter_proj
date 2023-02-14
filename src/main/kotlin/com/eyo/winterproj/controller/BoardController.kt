package com.eyo.winterproj.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/board") // board게시판은 localhost:8080/board을 입력하면
class BoardController {
    @GetMapping("/")
    fun index() : String {
        return "redirect:/board/list" //  @GetMapping("/list")실행
    }

    @GetMapping("/list")
    fun list() : String {
        return "board/list/index" // localhst:8080/board/list/index.html 실행
    }

}

