package com.eyo.winterproj.entity

import jakarta.persistence.*


@Entity
@Table (name = "Board")

data class BoardEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "article_id") val articleId: Int,
            val title: String,
            val content: String,
            val view: Int,
    @Column(name = "user_id") val userId: Int

)

