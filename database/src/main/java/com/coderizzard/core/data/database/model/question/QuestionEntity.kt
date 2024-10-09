package com.coderizzard.core.data.database.model.question


sealed interface QuestionEntity {
    val id : String
    val text : String
    val point : Int
    val quizId : String
}