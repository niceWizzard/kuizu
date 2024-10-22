package com.coderizzard.core.data.model.question

sealed interface Question {
    val id : String
    val text : String
    val point : Int
    val quizId : String
    val remoteId : String
}