package com.coderizzard.core.data.model.session.answer

data class IdentificationAnswer(
    override val quizId : String,
    override val questionId : String,
    override val isCorrect : Boolean,
    val correctAnswer : String,
) : SessionAnswer