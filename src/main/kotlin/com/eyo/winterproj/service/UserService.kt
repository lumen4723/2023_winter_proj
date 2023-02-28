package com.eyo.winterproj.service

import com.eyo.winterproj.entity.EmailEntity
import com.eyo.winterproj.entity.UserEntity
import com.eyo.winterproj.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional


@Service
class UserService(@Autowired val userRepository: UserRepository) {
    fun login(email: String,  password: String): Optional<UserEntity>{
        return userRepository.findByEmailAndPassword(email, password)
    }

    fun register(email:String, password:String, name:String, number:String): UserEntity {
        val user= UserEntity(0,email, password, name, number);
        userRepository.save(user)
        return user
    }

    fun findusername(userId: Int): String {
        if(userRepository.findByno(userId).isPresent){
            return userRepository.findByno(userId).get().name
        }
        return "삭제된 회원"
    }
}