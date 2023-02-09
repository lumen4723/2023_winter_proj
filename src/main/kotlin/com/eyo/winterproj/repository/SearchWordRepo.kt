package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.SearchWordEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SearchWordRepo : CrudRepository<SearchWordEntity, Long> {
}
