package com.coderizzard.quiz.data.model

import android.media.Image
import java.time.LocalDateTime

data class Quiz(
    val id : String,
    val image: Image? = null,
    val name : String,
    val author : String,
    val createdAt : LocalDateTime,
    val questions : List<Question>,
) {
}