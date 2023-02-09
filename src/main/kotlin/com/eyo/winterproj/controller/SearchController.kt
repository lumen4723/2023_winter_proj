package com.eyo.winterproj.controller

import com.eyo.winterproj.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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
}

data class SearchRequestEntity(val word: String)
