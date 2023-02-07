package com.eyo.winterproj.entity

import com.eyo.winrerproj.entity.NamuEntity
import com.eyo.winrerproj.entity.SearchWordEntity
import jakarta.persistence.*

@Entity
@Table(name = "search_word_reverse")
data class SearchWordReverseEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,
        @Column(name = "word_id" unique = true) var word_id: Long? = null,
        @Column(name = "board_id") var board_id: Long? = null,
        @Column(name = "count") var count: Int? = null,
        @Column(name = "weight") var weight: Float? = null,
        @ManyToOne
        @JoinColumn(name = "word_id", insertable = false, updatable = false)
        var search_word: SearchWordEntity? = null,
        @OneToOne
        @JoinColumn(name = "board_id", insertable = false, updatable = false)
        var namu: NamuEntity? = null,
)
