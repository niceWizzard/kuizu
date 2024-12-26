package com.coderizzard.core.data.placeholder.value

import com.coderizzard.core.data.model.session.QuizSession
import java.time.LocalDateTime

val quizSession =    QuizSession(
    quizId = quiz.id,
    currentQuestionIndex = 0,
    questionOrder = quiz.supportedQuestions.map { it.id },
    startedAt = LocalDateTime.now(),
    quiz = quiz,
)