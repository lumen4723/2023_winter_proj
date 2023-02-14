package com.eyo.winterproj.service

import com.eyo.winterproj.entity.NamuEntity
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
            searchWordReverseRepo.findByWordId(it.id!!).ifPresent { namu = it.map { it.namu!! } }
        }
        if (namu == null) {
            return Result.failure(Exception("검색 결과가 없습니다."))
        } else {
            return Result.success(namu!!)
        }
    }

    fun delete(id: Long): Result<Boolean> {
        var result = false
        searchWordReverseRepo.findByBoardId(id).ifPresent {
            namuRepo.findById(it.boardId!!).ifPresent { namuRepo.changeFlag(0) }
            searchWordRepo.findById(it.wordId!!).ifPresent { searchWordRepo.countDown(1) }
        }
        if (result) {
            return Result.success(result)
        } else {
            return Result.failure(Exception("삭제할 게시글이 없습니다."))
        }
    }
}
