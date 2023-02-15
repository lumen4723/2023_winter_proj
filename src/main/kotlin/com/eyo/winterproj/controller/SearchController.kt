package com.eyo.winterproj.controller

import com.eyo.winterproj.entity.CreateNamuDto
import com.eyo.winterproj.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import com.eyo.winterproj.entity.NamuEntity

@Controller
@RequestMapping("/namu")
class SearchController(@Autowired val searchService: SearchService) {
    @GetMapping("/")
    fun main(): String {
        return "namu/index"
    }

    @GetMapping("/error")
    fun error(): String {
        return "namu/error"
    }

    @GetMapping("/search")
    fun search(req: SearchRequestEntity, model: Model): String {
        val namu = searchService.search(req.word)

        if (namu.isFailure) {
            return "redirect:/namu/error/"
        }

        val namulist = namu.getOrNull()
        model.addAttribute("namus", pagination(namulist!!, req.page, req.limit))
        model.addAttribute("pagecount", namulist!!.size)
        
        return "namu/index"
    }

    fun pagination(namus: List<NamuEntity>, page: Int = 1, limit: Int = 10): List<NamuEntity> {
        val start = (page - 1) * limit
        val end = if (start + limit < namus.size) start + limit else namus.size 
        return namus.subList(start, end)
    }

    @GetMapping("/create")
    fun create(): String {
        return "namu_create"
    }

    @PostMapping("/create")
    fun create(req: CreateNamuDto, model: Model): String {
        val namu = searchService.create(req)

        if (namu.isFailure) {
            println("create failed")
            return "namu/namu_create"
        }
        model.addAttribute("namus", namu.getOrNull())
        println(namu)
        return "namu/index"
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    fun delete(@PathVariable id: Long): String {
        val result = searchService.delete(id)
        if (result.isFailure) {
            return "redirect:/namu/error/"
        }
        return "삭제되었습니다."
    }
}

data class SearchRequestEntity(
    val word: String,
    val page: Int = 1,
    val limit: Int = 2
)
