package com.coderizzard.database.data.database.model.session

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.database.data.database.model.QuizEntity
import java.time.LocalDateTime

@Entity(
    tableName = "session_result",
    foreignKeys = [
        ForeignKey(
            parentColumns = [QuizEntity.ID],
            entity = QuizEntity::class,
            childColumns = [SessionResultEntity.QUIZ_ID],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class SessionResultEntity(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = QUIZ_ID)
    val quizId: String,
    @ColumnInfo(name = TOTAL_POINTS)
    val totalPoints: Int,
    @ColumnInfo(name = MARKS)
    val marks: Int,
    @ColumnInfo(name = DATE_FINISHED)
    val dateFinished: LocalDateTime
) {
    companion object {
        const val ID = "id"
        const val QUIZ_ID = "quiz_id"
        const val TOTAL_POINTS = "total_points"
        const val MARKS = "marks"
        const val DATE_FINISHED = "date_finished"
    }

    fun toSessionResult() : SessionResult {
        return SessionResult(
            id = this.id,
            quizId = this.quizId,
            totalPoints = this.totalPoints,
            marks = this.marks,
            dateFinished = this.dateFinished,
        )
    }
}