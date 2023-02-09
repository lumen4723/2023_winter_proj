package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.NamuEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface NamuRepo : CrudRepository<NamuEntity, Long> {
}
