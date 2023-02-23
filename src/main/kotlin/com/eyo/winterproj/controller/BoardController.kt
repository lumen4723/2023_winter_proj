package com.eyo.winterproj.controller

import com.eyo.winterproj.service.BoardRepo
import com.eyo.winterproj.service.BoardService
// import com.eyo.winterproj.util.toPrint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import jakarta.servlet.http.HttpSession
import com.eyo.winterproj.entity.BoardEntity
import com.eyo.winterproj.entity.UserEntity
import com.eyo.winterproj.service.UserService
import com.eyo.winterproj.util.formatPrint

@Controller
@RequestMapping("/board") // board게시판은 localhost:8080/board을 입력하면 class가 2개면 무엇을 우선으로
class BoardController(
    @Autowired val boardService: BoardService,
    @Autowired val userService: UserService
) {
    @GetMapping("/")
    fun index() : String {
        return "redirect:/board/list" //  @GetMapping("/list")실행
    }

    @GetMapping("/list")
    fun list(md: Model) : String {
        val boardList = boardService.BoardRepo.findAll().map {
            BoardPrintEntity(
                articleId = it.articleId,
                title = it.title,
                content = it.content,
                created = it.created.formatPrint(),
                view = it.view,
                username = userService.findusername(it.userId)
            )
        }

        md.addAttribute("boardlist", boardList)
        return "board/list/index" // localhst:8080/board/list/index.html 실행
    }

    @GetMapping("/write")
    fun writePage(session: HttpSession): String {
        val user = session.getAttribute("user")
        return user?.let {
            "board/write/write"
        } ?: "redirect:/user/login?msg=LoginRequired"
    }

    @PostMapping("/write")
    fun writeArticle(article: WriteRequestEntity, session: HttpSession): String {
        val user = session.getAttribute("user") as UserEntity?
        if(user == null) return "redirect:/user/login?msg=LoginRequired"
        boardService.writeArticle(article.title, article.content, user.no)
        return "redirect:list"
    }

    @PostMapping("/{articleId}/delete")
    fun deleteArticle(@PathVariable articleId: Int): String {
        boardService.deleteArticle(articleId)
        return "redirect:/board/list"
    }

}
data class WriteRequestEntity (val title: String, val content: String)

data class BoardPrintEntity(val articleId: Int, val title: String, val content: String, val created: String, val view: Int, val username: String)

//data class DeleteRequestEntity (val articleId: Int)