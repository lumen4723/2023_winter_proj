package com.eyo.winterproj.controller

import com.eyo.winterproj.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/search")
class SearchController(@Autowired val searchService: SearchService) {
    @GetMapping("/namu/{word}")
    fun search(@PathVariable("word") word: String, model: Model): String {
        val namu = searchService.search(word)
        model.addAttribute("namu", namu)

        return "search"
    }
}

data class SearchRequestEntity(val word: String)
