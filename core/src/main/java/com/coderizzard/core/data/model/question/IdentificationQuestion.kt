package com.coderizzard.core.data.model.question

data class IdentificationQuestion(
    override val id: String,
    override val text: String,
    override val point: Int,
    override val quizId: String,
    val answer  : String,
)  : Question {
}