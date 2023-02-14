package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.SearchWordEntity
import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SearchWordRepo : CrudRepository<SearchWordEntity, Long> {
    fun findByWord(word: String): Optional<SearchWordEntity>
    fun countDown(amount: Int): Optional<SearchWordEntity>
}
