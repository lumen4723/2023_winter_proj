package com.eyo.winterproj.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name="email_check")
data class EmailEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) val no:Int,
    val toEmail:String,
    val authCode:String,
    val created:LocalDateTime
)