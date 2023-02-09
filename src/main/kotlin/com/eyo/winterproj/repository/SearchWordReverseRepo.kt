package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.SearchWordReverseEntity
import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SearchWordReverseRepo : CrudRepository<SearchWordReverseEntity, Long> {
    fun findByWordId(wordId : Long) : Optional<List<SearchWordReverseEntity>>
}
