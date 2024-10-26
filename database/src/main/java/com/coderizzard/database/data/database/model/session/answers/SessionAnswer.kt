package com.coderizzard.database.data.database.model.session.answers

sealed interface SessionAnswer {
    val sessionId : String
    val quizId : String
    val questionId : String
    val isCorrect : Boolean

    companion object {
        const val SESSION_ID = "session_id"
        const val QUIZ_ID = "quiz_id"
        const val QUESTION_ID = "question_id"
        const val IS_CORRECT = "is_correct"
    }
}