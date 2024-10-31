package com.coderizzard.core.data.model.session

import java.time.LocalDateTime
import java.util.UUID

data class SessionResult(
    val id : String = UUID.randomUUID().toString(),
    val quizId : String,
    val totalPoints : Int,
    val marks : Int,
    val dateFinished : LocalDateTime,
)