package com.eyo.winterproj.controller

import com.eyo.winterproj.service.BoardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/board") // board게시판은 localhost:8080/board을 입력하면 class가 2개면 무엇을 우선으로
class BoardController(@Autowired val boardService: BoardService) {
    @GetMapping("/")
    fun index() : String {
        return "redirect:/board/list" //  @GetMapping("/list")실행
    }

    @GetMapping("/list")
    fun list() : String {
        return "board/list/index" // localhst:8080/board/list/index.html 실행
    }

    @GetMapping("/write")
    fun writePage(): String {
        return "board/write/write"
    }

    @PostMapping("/write")
    fun writeArticle(article: WriteRequestEntity): String {
        boardService.writeArticle(article.title, article.content)
        return "redirect:list"
    }

}
data class WriteRequestEntity (
    val title: String,
    val content: String
)

