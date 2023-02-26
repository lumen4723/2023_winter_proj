package com.eyo.winterproj.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_member")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val no: Int,
    val email: String,
    val password: String,
    val name: String,
    val number:String,
)