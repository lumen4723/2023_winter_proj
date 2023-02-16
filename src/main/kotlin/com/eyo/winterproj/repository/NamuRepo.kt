package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.NamuEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Repository interface NamuRepo  : CrudRepository<NamuEntity, Long> {
    fun findAllBy(pageable: Pageable): Optional<List<NamuEntity>>
}
