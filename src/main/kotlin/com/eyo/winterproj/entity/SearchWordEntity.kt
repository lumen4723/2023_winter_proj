package com.eyo.winterproj.entity

import jakarta.persistence.*

@Entity
@Table(name = "search_word")
data class SearchWordEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,
        @Column(name = "word", unique = true) var word: String? = null,
        @Column(name = "count") var count: Int = 0,
)
