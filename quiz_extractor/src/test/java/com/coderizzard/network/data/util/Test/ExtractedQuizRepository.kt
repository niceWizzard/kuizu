package com.coderizzard.network.data.util.Test

import com.coderizzard.network.data.model.ExtractedQuiz
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.data.repository.ExtractedQuizRepositoryImpl
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.network.domain.QuizExtractorApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.UUID

class ExtractedQuizRepository {
    lateinit var repository: ExtractedQuizRepository

    @Before
    fun initialize() {
        repository = ExtractedQuizRepositoryImpl(
            MockQuizExtractorApi()
        )
    }

    @Test
    fun testValidCases() = runBlocking{
        val quiz = repository.extractQuizById("1")
        Assert.assertTrue(
            quiz is ApiResponse.Success
        )
    }

    @Test
    fun testErrorCases() = runBlocking {
        val quiz = repository.extractQuizById(UUID.randomUUID().toString())
        Assert.assertTrue(
            quiz is ApiResponse.Error
        )
    }

}

private class MockQuizExtractorApi : QuizExtractorApi {
    override suspend fun extractQuizById(quizId: String): ExtractedQuiz {
        val validQuiz = ExtractedQuiz(
            name = "valid",
            author = "valid",
            imageLink = "valid",
            questionList = emptyList(),
            createdAt = LocalDateTime.now()
        )
        return when(quizId) {
            "1" -> validQuiz
            else -> throw Exception("Unhandled quiz id")
        }
    }

}