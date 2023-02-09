package com.eyo.winterproj.entity



import jakarta.persistence.*


@Entity
@Table (name = "Board")

data class BoardEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val article_id: Int,
            val title: String,
            val content: String,
            val view: Int,
            val user_id: Int

)