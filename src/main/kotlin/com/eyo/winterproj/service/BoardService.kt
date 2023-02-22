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

    fun updateArticle(updateId: Int, title: String, content: String): Optional<BoardEntity> {
        val updAr = BoardRepo.findByArticleId(updateId).get()
        if(updAr.articleId == updateId) {
            updAr.title = title
            updAr.content = content
            if(updAr.title != "" && updAr.content != "") {
                BoardRepo.save(updAr)
            }
        }
        return BoardRepo.findByArticleId(updateId)
    }

}
