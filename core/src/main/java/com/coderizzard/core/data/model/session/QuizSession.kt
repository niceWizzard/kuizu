package com.coderizzard.core.data.model.session

import com.coderizzard.core.data.model.Quiz
import java.time.LocalDateTime

data class QuizSession(
    val id : String,
    val startedAt : LocalDateTime,
    val quizId : String,
    val quiz : Quiz
)