package com.coderizzard.database.data.database.model.session.answers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.IS_CORRECT
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.QUESTION_ID
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.QUIZ_ID
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.SESSION_ID

@Entity(
    "identification_answer",
    foreignKeys = [
        ForeignKey(
            parentColumns = ["id"],
            entity = QuizEntity::class,
            childColumns = [QUIZ_ID],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            parentColumns = ["id"],
            entity = QuizSessionEntity::class,
            childColumns = [SESSION_ID],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    primaryKeys = [
        QUIZ_ID,
        QUESTION_ID
    ]
)
internal data class IdentificationAnswerEntity (
    @ColumnInfo(SESSION_ID)
    override val sessionId : String,
    @ColumnInfo(QUIZ_ID)
    override val quizId : String,
    @ColumnInfo(QUESTION_ID)
    override val questionId : String,
    @ColumnInfo(IS_CORRECT)
    override val isCorrect : Boolean,
    @ColumnInfo(ANSWER)
    val correctAnswer : String,
) : SessionAnswer {
    companion object {
        const val ANSWER = "correct_answer"
    }

    fun toIdentificationAnswer() : IdentificationAnswer {
        return IdentificationAnswer(
            sessionId = this.sessionId,
            questionId = this.questionId,
            quizId = this.quizId,
            isCorrect = this.isCorrect,
            correctAnswer = this.correctAnswer,
        )
    }
}