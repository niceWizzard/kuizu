package com.coderizzard.core.data.placeholder.value

import com.coderizzard.core.data.model.Quiz
import java.time.LocalDateTime

val quiz = Quiz(
    name = "Quiz 1",
    imageLink = "",
    author = "Author 1",
    allQuestions = listOf(identificationQuestion, multipleChoiceQuestion),
    id = QUIZ_ID_1,
    createdAt = LocalDateTime.now(),
    remoteId = "quiz-remote-1",
)