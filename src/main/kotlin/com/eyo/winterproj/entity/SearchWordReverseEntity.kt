package com.eyo.winterproj.entity

import jakarta.persistence.*

@Entity
@Table(
        name = "search_word_reverse",
        uniqueConstraints =
                [UniqueConstraint(name = "word_namu", columnNames = ["word_id", "namu_id"])]
)
data class SearchWordReverseEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,
        @Column(name = "word_id") var wordId: Long? = null,
        @Column(name = "namu_id") var namuId: Long? = null,
        @Column(name = "count") var count: Int? = null,
        @Column(name = "weight") var weight: Float? = null,
        @OneToOne
        @JoinColumn(name = "word_id", insertable = false, updatable = false)
        var searchWord: SearchWordEntity? = null,
        @OneToOne
        @JoinColumn(name = "namu_id", insertable = false, updatable = false)
        var namu: NamuEntity? = null,
)
