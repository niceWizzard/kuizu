package com.coderizzard.core.data.placeholder.value

import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCOption
import com.coderizzard.core.data.model.question.MCQuestion

const val QUIZ_ID_1 = "quiz-id-1"

val identificationQuestion = IdentificationQuestion(
    id = "idq-1",
    imageLink = "",
    text = "What is 1 + 1?",
    point = 1,
    quizId = QUIZ_ID_1,
    remoteId = "remote-idq-1",
    answer = "2",
)

val mcqOptions = List(5) {
    val n = it + 1
    MCOption(
        remoteId = "mcoption-remote-${n}",
        text = "$n",
        id = "mc-opt-${n}",
        questionId = "mcq-1"
    )
}

val multipleChoiceQuestion = MCQuestion(
    id = "mcq-1",
    text = "What is 2 + 2?",
    answer = listOf("mc-opt-4"),
    quizId = QUIZ_ID_1,
    remoteId = "remote-mcq-1",
    point = 1,
    imageLink = "",
    options = mcqOptions,
)
