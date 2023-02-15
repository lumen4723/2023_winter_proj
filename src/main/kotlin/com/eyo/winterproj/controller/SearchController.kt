package com.eyo.winterproj.controller

import com.eyo.winterproj.entity.CreateNamuDto
import com.eyo.winterproj.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/namu")
class SearchController(@Autowired val searchService: SearchService) {
    @GetMapping("/")
    fun main(): String {
        return "namu"
    }

    @GetMapping("/search")
    fun search(req: SearchRequestEntity, model: Model): String {
        val namu = searchService.search(req.word)

        if (namu.isFailure) {
            return "redirect:/"
        }
        model.addAttribute("namus", namu.getOrNull())
        println(namu)
        return "namu"
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
            return "namu_create"
        }
        model.addAttribute("namus", namu.getOrNull())
        println(namu)
        return "namu"
    }
}

data class SearchRequestEntity(val word: String)
