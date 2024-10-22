package com.coderizzard.core.data.model.question

data class MCQuestion(
    override val id: String,
    override val text: String,
    override val point: Int,
    override val quizId: String,
    val options : List<String>,
    val answer : List<Int>,
)  : Question {
}