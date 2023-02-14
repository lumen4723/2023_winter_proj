package com.eyo.winterproj.entity

import jakarta.persistence.*

@Entity
@Table(name = "namu")
data class NamuEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long? = null,
        @Column(name = "title", columnDefinition = "LONGTEXT") var title: String? = null,
        @Column(name = "content", columnDefinition = "LONGTEXT") var content: String? = null,
        @Column(name = "namespace") var namespace: String? = null,
)
