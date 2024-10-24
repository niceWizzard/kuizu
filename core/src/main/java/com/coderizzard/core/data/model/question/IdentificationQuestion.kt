package com.coderizzard.core.data.model.question

data class IdentificationQuestion(
    override val id: String,
    override val text: String,
    override val point: Int,
    override val quizId: String,
    override val remoteId: String,
    override val imageLink: String = "",
    override val localImagePath: String = "",
    val answer  : String,
)  : Question {
}