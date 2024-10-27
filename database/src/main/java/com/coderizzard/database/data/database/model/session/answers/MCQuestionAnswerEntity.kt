package com.coderizzard.database.data.database.model.session.answers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.coderizzard.core.data.model.session.answer.MCQuestionAnswer
import com.coderizzard.database.data.database.model.QuizEntity
import com.coderizzard.database.data.database.model.session.QuizSessionEntity
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.IS_CORRECT
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.QUESTION_ID
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.QUIZ_ID
import com.coderizzard.database.data.database.model.session.answers.SessionAnswer.Companion.SESSION_ID

@Entity(
    "mc_question_answer",
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
data class MCQuestionAnswerEntity (
    @ColumnInfo(SESSION_ID)
    override val sessionId : String,
    @ColumnInfo(QUIZ_ID)
    override val quizId : String,
    @ColumnInfo(QUESTION_ID)
    override val questionId : String,
    @ColumnInfo(IS_CORRECT)
    override val isCorrect : Boolean,
    @ColumnInfo(ANSWER_ID)
    val correctAnswerIds : List<String>,
) : SessionAnswer {
    companion object {
        const val ANSWER_ID = "answer_ids"
    }

    fun toMCQuestionAnswer() : MCQuestionAnswer {
        return MCQuestionAnswer(
            sessionId = this.sessionId,
            correctAnswerIds = this.correctAnswerIds,
            questionId = this.questionId,
            quizId = this.quizId,
            isCorrect = this.isCorrect,
        )
    }
}