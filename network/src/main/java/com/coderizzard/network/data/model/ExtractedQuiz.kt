package com.coderizzard.network.data.model

import java.time.LocalDateTime

data class ExtractedQuiz(
    val name : String,
    val author : String,
    val imageLink : String,
    val createdAt : LocalDateTime,
    val questionList : List<ExtractedQuestion>
)
