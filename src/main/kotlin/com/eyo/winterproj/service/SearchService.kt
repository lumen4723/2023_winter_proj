package com.eyo.winterproj.service

import com.eyo.winterproj.entity.NamuEntity
import com.eyo.winterproj.repository.NamuRepository
import com.eyo.winterproj.repository.SearchWordRepository
import com.eyo.winterproj.repository.SearchWordReverseRepository
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SearchService(
        @Autowired val searchWordRepository: SearchWordRepository,
        @Autowired val searchWordReverseRepository: SearchWordReverseRepository,
        @Autowired val namuRepository: NamuRepository
) {
    fun search(word: String): List<NamuEntity> {
        val namu = namuRepository.findByTitle(word)
        return listOf(namu)
    }
}
