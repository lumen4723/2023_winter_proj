package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.SearchWordEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SearchWordRepo : CrudRepository<SearchWordEntity, Long> {
    fun findByWord(word: String): Optional<SearchWordEntity>
}
