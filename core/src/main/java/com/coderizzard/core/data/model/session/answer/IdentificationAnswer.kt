package com.coderizzard.core.data.model.session.answer

data class IdentificationAnswer(
    override val sessionId : String,
    override val quizId : String,
    override val questionId : String,
    override val isCorrect : Boolean,
    val answer : String,
) : SessionAnswer