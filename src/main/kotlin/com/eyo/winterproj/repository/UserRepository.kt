package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<UserEntity, Int>  {
    fun findByEmail(email: String): UserEntity?
    fun findByEmailAndPassword(email: String, password: String): Optional<UserEntity>
    fun findByno(no: Int): Optional<UserEntity>
}