package com.coderizzard.core.data.model

import android.media.Image
import com.coderizzard.core.data.model.question.Question
import java.time.LocalDateTime

data class Quiz(
    val id : String,
    val image: Image? = null,
    val name : String,
    val author : String,
    val createdAt : LocalDateTime,
    val questions : List<Question>,
    val imageLink : String = "",
    val remoteId : String ,
) {
}