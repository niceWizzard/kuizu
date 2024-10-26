package com.coderizzard.core.data.model.session.answer

 sealed interface SessionAnswer {
     val sessionId: String
     val quizId: String
     val questionId: String
     val isCorrect: Boolean
 }