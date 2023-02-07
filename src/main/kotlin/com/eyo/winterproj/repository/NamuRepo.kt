package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.NamuEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NamuRepo : CrudRepository<NamuEntity, Long> {
    fun findByTitle(title: String): NamuEntity?
}
