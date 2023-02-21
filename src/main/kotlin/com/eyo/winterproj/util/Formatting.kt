package com.eyo.winterproj.util

import com.eyo.winterproj.controller.BoardPrintEntity
import com.eyo.winterproj.entity.BoardEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatPrint(): String = this.format(DateTimeFormatter.ofPattern("yyy-MM-dd"))

// fun Iterable<BoardEntity>.toPrint() = this.map {
//     BoardPrintEntity(
//         articleId = it.articleId,
//         title = it.title,
//         content = it.content,
//         created = it.created.formatPrint(),
//     )
// }