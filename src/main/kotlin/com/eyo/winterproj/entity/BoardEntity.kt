package com.eyo.winterproj.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table (name = "Board")

data class BoardEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "article_id") val articleId: Int,
    var title: String,
    var content: String,
    val created: LocalDateTime,
    @GeneratedValue(strategy = GenerationType.IDENTITY) val view: Int,
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id") val userId: Int

)

