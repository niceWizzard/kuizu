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
            parentColumns = [QuizEntity.ID],
            entity = QuizEntity::class,
            childColumns = [QuizSessionEntity.QUIZ_ID],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class QuizSessionEntity(
    @PrimaryKey
    @ColumnInfo(QUIZ_ID)
    val quizId : String,
    @ColumnInfo(STARTED_AT)
    val startedAt : LocalDateTime,
    @ColumnInfo(QUESTION_ORDER)
    val questionOrder : List<String>,
    @ColumnInfo(CURRENT_QUESTION_INDEX)
    val currentQuestionIndex : Int=0,
) {
    companion object {
        const val QUIZ_ID = "quiz_id"
        const val STARTED_AT = "started_at"
        const val QUESTION_ORDER = "question_order"
        const val CURRENT_QUESTION_INDEX = "current_question_index"
    }
    fun toQuizSession(quiz : Quiz) : QuizSession {
        return QuizSession(
            quizId = this.quizId,
            quiz = quiz,
            startedAt = this.startedAt,
            currentQuestionIndex = this.currentQuestionIndex,
            questionOrder = this.questionOrder,
        )
    }
}