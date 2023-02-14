package com.eyo.winterproj.service

import com.eyo.winterproj.entity.BoardEntity
import org.springframework.data.repository.CrudRepository

interface BoardRepo : CrudRepository<BoardEntity, Int> {
    //fun findByArticleId(articleId: Int): Optional<BoardEntity>
}