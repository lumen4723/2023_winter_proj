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
import org.springframework.stereotype.Service

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
                namu = it.filter { it.namu!!.flag == 0 }.map { it.namu!! }
            }
        }
        if (namu == null) {
            return Result.failure(Exception("검색 결과가 없습니다."))
        } else {
            return Result.success(namu!!)
        }
    }

    fun delete(id: Long): Result<Boolean> {
        var result = false
        val searchWordReverseRepo = searchWordReverseRepo.findByNamuId(id).get()
        searchWordReverseRepo.namu!!.flag = 1
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

    fun create(input: CreateNamuDto): Result<NamuEntity> {
        val namu = NamuEntity(
                id = 0,
                title = input.title,
                content = input.content,
                flag = 1,
        )
        val saved_namu = namuRepo.save(namu)
        make_map(input.title, input.content).iterator().forEach{
            val wordEntity = searchWordRepo.findByWord(it.key).orElseGet {
                val wordEntity = SearchWordEntity(
                        id = 0,
                        word = it.key,
                        count = 0
                )
                searchWordRepo.save(wordEntity)
                wordEntity
            }
            wordEntity.count = wordEntity.count!! + it.value
            val saved_word =  searchWordRepo.save(wordEntity)
            val wordReverseEntity = SearchWordReverseEntity(
                    id = 0,
                    wordId = saved_word.id!!,
                    namuId = saved_namu.id!!,
                    count = it.value,
                    weight = it.value/ Math.log(saved_word.count+1.0)
            )
            searchWordReverseRepo.save(wordReverseEntity)
        }
        return Result.success(saved_namu)
    }

    infix fun String.input_map(map: MutableMap<String, Int>): MutableMap<String, Int> {
        this.windowed(2).forEach {
            map[it] = map.getOrDefault(it, 0) + 1
        }
        return map
    }

    fun make_map(title: String, content: String) : MutableMap<String, Int> {
        // hash<string, int> 생성
        var map = mutableMapOf<String, Int>()
        map = title.input_map(map)
        map = content.input_map(map)
        return map
    }
}
