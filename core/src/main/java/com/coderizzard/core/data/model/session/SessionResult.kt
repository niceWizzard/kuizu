package com.coderizzard.core.data.model.session

import java.time.LocalDateTime

data class SessionResult(
    val id : String,
    val quizId : String,
    val totalPoints : Int,
    val marks : Int,
    val dateFinished : LocalDateTime,
)