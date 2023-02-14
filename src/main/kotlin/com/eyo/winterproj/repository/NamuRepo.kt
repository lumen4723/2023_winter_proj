package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.NamuEntity
import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NamuRepo : CrudRepository<NamuEntity, Long> {
    fun changeFlag(flag: Int): Optional<NamuEntity>
}
