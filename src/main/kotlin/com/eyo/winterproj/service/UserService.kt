package com.eyo.winterproj.service

import com.eyo.winterproj.entity.UserEntity
import com.eyo.winterproj.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class UserService(@Autowired val userRepository: UserRepository) {
    fun login(email: String,  password: String): Optional<UserEntity>{
        return userRepository.findByEmailAndPassword(email, password)
    }
}