package com.coderizzard.database.data.database.model.session.answers

import com.coderizzard.core.data.model.session.answer.SessionAnswer

sealed interface SessionAnswer {
    fun toSessionAnswer() : SessionAnswer {
        return when(this) {
            is IdentificationAnswerEntity -> this.toIdentificationAnswer()
            is MCQuestionAnswerEntity -> this.toMCQuestionAnswer()
        }
    }

    val quizId : String
    val questionId : String
    val isCorrect : Boolean

    companion object {
        const val QUIZ_ID = "quiz_id"
        const val QUESTION_ID = "question_id"
        const val IS_CORRECT = "is_correct"
    }
}