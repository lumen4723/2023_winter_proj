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
        @Autowired val searchWordRepository: SearchWordRepo,
        @Autowired val searchWordReverseRepository: SearchWordReverseRepo,
        @Autowired val namuRepository: NamuRepo
) {
    fun search(word: String): Result<List<NamuEntity>> {
        var namu: List<NamuEntity>? = null;
        searchWordRepository.findByWord(word).ifPresent{
            searchWordReverseRepository.findByWordId(it.id!!).ifPresent{
                namu = it.map {
                    it.namu!!
                }
            }
        }
        if (namu == null) {
            return Result.failure(Exception("검색 결과가 없습니다."))
        } else {
            return Result.success(namu!!)
        }
    }
}
