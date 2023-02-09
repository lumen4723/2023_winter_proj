package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.SearchWordReverseEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SearchWordReverseRepo : CrudRepository<SearchWordReverseEntity, Long> {
}
