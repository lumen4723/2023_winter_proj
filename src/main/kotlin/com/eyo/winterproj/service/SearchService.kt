package com.eyo.winterproj.service

import com.eyo.winterproj.entity.CreateNamuDto
import com.eyo.winterproj.entity.NamuEntity
import com.eyo.winterproj.entity.SearchWordEntity
import com.eyo.winterproj.entity.SearchWordReverseEntity
import com.eyo.winterproj.repository.NamuRepo
import com.eyo.winterproj.repository.SearchWordRepo
import com.eyo.winterproj.repository.SearchWordReverseRepo
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page

@Service
class SearchService(
        @Autowired val searchWordRepo: SearchWordRepo,
        @Autowired val searchWordReverseRepo: SearchWordReverseRepo,
        @Autowired val namuRepo: NamuRepo,
) {
    fun search(word: String): Result<List<NamuEntity>> {
        var namu: List<NamuEntity>? = null
        searchWordRepo.findByWord(word).ifPresent {
            searchWordReverseRepo.findByWordId(it.id!!).ifPresent {
                namu = it.filter { it.namu!!.flag == 1 }.map { it.namu!! }
            }
        }
        if (namu == null) {
            return Result.failure(Exception("검색 결과가 없습니다."))
        } else {
            return Result.success(namu!!)
        }
    }

    fun create(input: CreateNamuDto): Result<NamuEntity> {
        val namu =
                NamuEntity(
                        id = 0,
                        title = input.title,
                        content = input.content,
                        flag = 1,
                )
        val saved_namu = namuRepo.save(namu)
        make_map(input.title, input.content).create_tables_from_namu(saved_namu.id!!)
        return Result.success(saved_namu)
    }

    fun delete(id: Long): Result<Boolean> {
        var result = false
        val searchWordReverseRepo = searchWordReverseRepo.findByNamuId(id).get()
        searchWordReverseRepo.namu!!.flag = 0
        searchWordReverseRepo.searchWord!!.count =
                searchWordReverseRepo.searchWord!!.count!! - searchWordReverseRepo.count!!
        namuRepo.save(searchWordReverseRepo.namu!!)
        searchWordRepo.save(searchWordReverseRepo.searchWord!!)

        if (result) {
            return Result.success(result)
        } else {
            return Result.failure(Exception("삭제할 게시글이 없습니다."))
        }
    }

    infix fun String.input_map(map: MutableMap<String, Int>): MutableMap<String, Int> {
        this.windowed(2).forEach { map[it] = map.getOrDefault(it, 0) + 1 }
        return map
    }

    fun make_map(title: String, content: String): MutableMap<String, Int> {
        // hash<string, int> 생성
        var map = mutableMapOf<String, Int>()
        map = title.replace("\\p{Zs}+".toRegex(), "").toLowerCase().input_map(map)
        map = content.replace("\\p{Zs}+".toRegex(), "").toLowerCase().input_map(map)
        return map
    }

    infix fun MutableMap<String,Int>.create_tables_from_namu(namuId: Long) {
        this.iterator().forEach {
            val wordEntity =
                    searchWordRepo.findByWord(it.key).orElseGet {
                        val wordEntity = SearchWordEntity(id = 0, word = it.key, count = 0)
                        val savedWordEntity = searchWordRepo.save(wordEntity)
                        savedWordEntity
                    }
            wordEntity.count = wordEntity.count!! + it.value
            val saved_word = searchWordRepo.save(wordEntity)
            val wordReverseEntity =
                    SearchWordReverseEntity(
                            id = 0,
                            wordId = saved_word.id!!,
                            namuId = namuId,
                            count = it.value,
                            weight = it.value / Math.log(saved_word.count + 1.0)
                    )
            searchWordReverseRepo.save(wordReverseEntity)
        }
    }

    fun create_namu_to_word(start: Int, limit: Int): Result<String>{
        val pageRequest = PageRequest.of(start, limit)
        val namus = namuRepo.findAllBy(pageRequest)
        namus.ifPresent{
            it.forEach {
                println("------------------------------------------------------------------")
                println(" id: "+it.id)
                println("title: "+it.title)
                println(" content: "+it.content)
                val namuId = it.id!!
                println("------------------------------------------------------------------")
                println(make_map(it.title!!, it.content!!))
                make_map(it.title!!, it.content!!).create_tables_from_namu(namuId)
            }
        }
        println("------------------------------------------------------------------")
        return Result.success("success")
    }
}
