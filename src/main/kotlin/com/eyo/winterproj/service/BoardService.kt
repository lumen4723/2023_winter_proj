package com.eyo.winterproj.service

import com.eyo.winterproj.entity.BoardEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class BoardService(@Autowired val BoardRepo : BoardRepo) {
    fun writeArticle(title: String, content: String, userId: Int) : BoardEntity {
        val article = BoardEntity(0, title, content, LocalDateTime.now() ,0, userId)
        BoardRepo.save(article)
        return article
    }

    fun deleteArticle(articleId: Int): Optional<BoardEntity> {
        val delAr = BoardRepo.findByArticleId(articleId).get()
        if(delAr.articleId == articleId) { //의미없는 코드?
            BoardRepo.deleteById(articleId)
        }
        return BoardRepo.findByArticleId(articleId)
    }

}
