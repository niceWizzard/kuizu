package com.coderizzard.core.data.model.session.answer

 sealed interface SessionAnswer {
     val quizId: String
     val questionId: String
     val isCorrect: Boolean
 }