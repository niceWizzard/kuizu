package com.coderizzard.quiz.data.model

sealed interface Question {
    val id : String
    val text : String
    val point : Int
    val quizId : String
}