package com.coderizzard.core.data.model.session.answer

data class MCQuestionAnswer(
    override val sessionId : String,
    override val quizId : String,
    override val questionId : String,
    override val isCorrect : Boolean,
    val answerId : String,
)  : SessionAnswer