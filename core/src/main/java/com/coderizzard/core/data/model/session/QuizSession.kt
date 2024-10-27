package com.coderizzard.core.data.model.session

import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.Question
import java.time.LocalDateTime

data class QuizSession(
    val startedAt : LocalDateTime,
    val quizId : String,
    val quiz : Quiz,
    val questionOrder : List<String>,
    val currentQuestionIndex : Int,
) {
    fun getCurrentQuestion() : Question {
        return quiz.questions.find { it.id == questionOrder[currentQuestionIndex] } ?: throw Exception("Invalid id received.")
    }

    fun incrementQuestionIndex(): QuizSession {
        return copy(
            currentQuestionIndex = currentQuestionIndex + 1,
        )
    }

    fun hasNextQuestion() : Boolean {
        return currentQuestionIndex < questionOrder.size
    }
}