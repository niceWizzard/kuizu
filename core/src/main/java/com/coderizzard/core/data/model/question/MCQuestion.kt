package com.coderizzard.core.data.model.question

data class MCQuestion(
    override val id: String,
    override val remoteId: String,
    override val text: String,
    override val point: Int,
    override val quizId: String,
    override val imageLink: String = "",
    override val localImagePath: String = "",
    val options : List<MCOption>,
    val answer : List<String>,
)  : Question {

}