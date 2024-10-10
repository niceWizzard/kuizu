package com.coderizzard.quiz.data.model

data class MultipleChoiceQuestion(
    override val id: String,
    override val text: String,
    override val point: Int,
    override val quizId: String,
    val options : List<String>,
    val answer : List<Int>,
)  : Question{
}