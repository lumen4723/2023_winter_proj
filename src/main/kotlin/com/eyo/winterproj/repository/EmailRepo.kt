package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.EmailEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface  EmailRepo: CrudRepository<EmailEntity, Int> {
}