package com.eyo.winterproj.controller

import com.eyo.winterproj.entity.CreateNamuDto
import com.eyo.winterproj.entity.NamuEntity
import com.eyo.winterproj.entity.UpdateNamuDto
import com.eyo.winterproj.repository.NamuRepo
import com.eyo.winterproj.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/namu")
class SearchController(
        @Autowired val searchService: SearchService,
        @Autowired val namuRepo: NamuRepo
) {
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
        // req.word 를 windowed(2) 하고 각각의 값을 searchService.search하고 나온결과를 namu에 넣는다.
        // namu :: String(req.word) -> List<String>(.windowed(2))
        // -> List<Result<List<NamuEntity>>>(.map { searchService.search(it) }.filter { it.isSuccess })
        // -> List<NamuEntity>(.flatMap { it.getOrNull()!! })
        val namu = req.word.windowed(2).map { searchService.search(it) }.filter { it.isSuccess }.flatMap { it.getOrNull()!! }

        // val namu = searchService.search(req.word)

        if (namu.isEmpty()) {
            return "redirect:/namu/error/"
        }

        // val namulist = namu.getOrNull()
        // 여기 아래는 테스트가 안되서 작동여부를 모르겠음. 추가해야될건
        // 위에 값이 중복이 될텐데 중복을 카운트해서 카운트가 높은것을 최우선으로 보여줘야함.
        model.addAttribute("namus", pagination(namu, req.page, req.limit))
        model.addAttribute("pagecount", pagecount(namu, req.limit))

        return "namu/index"
    }

    fun pagination(namus: List<NamuEntity>, page: Int = 1, limit: Int = 10): List<NamuEntity> {
        val start = (page - 1) * limit
        val end = if (start + limit < namus.size) start + limit else namus.size
        var showlist = namus.subList(start, end)
        val textcut = 100 // limit text length in list
        showlist.forEach{ 
            if(it.content!!.length > textcut) it.content = it.content!!.substring(0, textcut) + "..." }
        return showlist
    }
    
    fun pagecount(namus: List<NamuEntity>, limit: Int = 10): Int {
        return namus.size / limit + if(namus.size % limit == 0) 0 else 1
    }

    @GetMapping("/create")
    fun create(): String {
        return "namu_create"
    }

    @PostMapping("/create")
    @ResponseBody
    fun create(req: CreateNamuDto, model: Model): String {
        val namu = searchService.create(req)

        if (namu.isFailure) {
            println("create failed")
            return "redirect:/namu/error/"
        }
        model.addAttribute("namus", namu.getOrNull())
        println(namu)
        return "생성되었습니다"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val namu = namuRepo.findById(id)

        if (namu.isEmpty) {
            return "redirect:/namu/error/"
        }
        model.addAttribute("namu", namu.get())
        return "namu/namu_edit"
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long, model: Model): String {
        val namu = namuRepo.findById(id)

        if (namu.isEmpty) {
            return "redirect:/namu/error/"
        }
        model.addAttribute("namu", namu.get())
        return "namu/namu_detail"
    }

    @PutMapping("/{id}")
    @ResponseBody
    fun update(@PathVariable id: Long, req: UpdateNamuDto): String {
        val result = searchService.update(id, req)
        if (result.isFailure) {
            return "redirect:/namu/error/"
        }
        return "수정되었습니다."
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    fun delete(@PathVariable id: Long): String {
        val result = searchService.delete(id,true,true)
        if (result.isFailure) {
            return "redirect:/namu/error/"
        }
        return "삭제되었습니다."
    }

    @PostMapping("/create/namuToWord")
    @ResponseBody
    fun namuToWord(req: namu_to_word_limit): String {
        val result = searchService.create_namu_to_word(req.start, req.limit)
        if (result.isFailure) {
            println("create failed")
            return "redirect:/namu/create"
        }
        return "redirect:/namu/create"
    }

    @DeleteMapping("/exceptNamu/{id}")
    @ResponseBody
    fun deleteExceptNamu(@PathVariable id: Long): String {
        val result = searchService.delete(id,false,true)
        if (result.isFailure) {
            return "redirect:/namu/error/"
        }
        return "삭제되었습니다."
    }
}

data class SearchRequestEntity(val word: String, val page: Int = 1, val limit: Int = 10)

data class namu_to_word_limit(val start: Int = 0, val limit: Int = 10)
