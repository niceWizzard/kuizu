package com.coderizzard.database.data.database.model.session

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.database.data.database.model.QuizEntity
import java.time.LocalDateTime

@Entity(
    "quiz_session",
    foreignKeys = [
        ForeignKey(
            parentColumns = ["id"],
            entity = QuizEntity::class,
            childColumns = ["quiz_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
internal data class QuizSessionEntity(
    @PrimaryKey
    val id : String,
    @ColumnInfo("quiz_id")
    val quizId : String,
    @ColumnInfo("started_at")
    val startedAt : LocalDateTime
) {
    fun toQuizSession(quiz : Quiz) : QuizSession {
        return QuizSession(
            id = this.id,
            quizId = this.quizId,
            quiz = quiz,
            startedAt = this.startedAt,
        )
    }
}