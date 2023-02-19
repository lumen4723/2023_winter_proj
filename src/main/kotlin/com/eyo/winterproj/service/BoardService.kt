package com.eyo.winterproj.service

import com.eyo.winterproj.entity.BoardEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BoardService(@Autowired val BoardRepo : BoardRepo) {
    fun writeArticle(title: String, content: String) : BoardEntity {
        val article = BoardEntity(0, title, content, 0, 0)
        BoardRepo.save(article)
        return article
    }
}