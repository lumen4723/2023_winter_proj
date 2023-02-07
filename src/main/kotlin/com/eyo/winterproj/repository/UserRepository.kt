package com.eyo.winterproj.repository

import com.eyo.winterproj.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

class UserRepository {
    @Repository
    interface UserRepo : CrudRepository<UserEntity, Int> {
        fun findByEmail(email: String): UserEntity?
        fun findByEmailAndPassword(email: String, password: String): Optional<UserEntity>
    }

}