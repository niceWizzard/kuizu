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
            parentColumns = ["id"],
            entity = QuizEntity::class,
            childColumns = ["quiz_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
internal data class SessionResultEntity(
    @PrimaryKey
    val id : String,
    @ColumnInfo("quiz_id")
    val quizId : String,
    @ColumnInfo("total_points")
    val totalPoints : Int,
    @ColumnInfo("marks")
    val marks : Int,
    @ColumnInfo("date_finished")
    val dateFinished : LocalDateTime
) {
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