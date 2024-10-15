package com.coderizzard.network.data.util

import com.coderizzard.network.data.model.ExtractedQuiz
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.data.repository.ExtractedQuizRepositoryImpl
import com.coderizzard.network.data.repository.InvalidQuizizzIdOrUrl
import com.coderizzard.network.domain.QuizExtractorApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDateTime

class TestExtractedQuizRepository {
    private lateinit var repo: ExtractedQuizRepositoryImpl
    private lateinit var api: QuizExtractorApi
    private val validQuiz = ExtractedQuiz(
        name = "valid",
        author = "valid",
        imageLink = "valid",
        questionList = emptyList(),
        createdAt = LocalDateTime.now()
    )
    @Before
    fun initialize() {
        api = mockk<QuizExtractorApi>()
        repo = ExtractedQuizRepositoryImpl(api)

    }
    @Test
    fun testValidCases() = runBlocking{
        coEvery { api.extractQuizById("1") } returns validQuiz
        val quiz = repo.extractQuizById("1")
        Assert.assertTrue(
            quiz is ApiResponse.Success
        )
        if(quiz is ApiResponse.Success) {
            Assert.assertEquals(quiz.value, validQuiz)
        }
    }

    @Test
    fun testErrorCases() = runBlocking {
        val badError = Exception("Bad")
        coEvery { api.extractQuizById("error") } throws badError
        val quiz = repo.extractQuizById("error")
        Assert.assertTrue(
            quiz is ApiResponse.Error
        )
        if(quiz is ApiResponse.Error) {
            Assert.assertEquals(
                "The error message should be prefixed but got ${quiz.message}",
                quiz.message.lowercase(),
                "Some error happened: \n${badError}".lowercase(),
            )
        }
    }

    @Test
    fun testQuizizzError() = runBlocking {
        val message = "Custom error"
        val error = HttpException(
            Response.error<Any>(
                400,
                ResponseBody.create(
                    MediaType.get("application/json"),
                    """{
                        |"success": false,
                        |"message": "${message}"
                        |}""".trimMargin()
                )
            )
        )
        coEvery { api.extractQuizById("error") } throws error

        val quiz = repo.extractQuizById("error")

        Assert.assertTrue(quiz is ApiResponse.Error)

        val receivedError = "Quizizz error: ${message}"
        if(quiz is ApiResponse.Error) {
            Assert.assertEquals(
                "Error message not equal, received: <${quiz.message}>, should have: <${receivedError}>",
                quiz.message.lowercase(),
                receivedError.lowercase()
            )
        }


    }

    @Test
    fun testInvalidIdOrUrl() = runBlocking {
        coEvery { api.extractQuizById("!") } returns validQuiz
        val quiz = repo.extractQuizById("!")
        Assert.assertTrue(quiz is ApiResponse.Error)
        if(quiz is ApiResponse.Error) {
            val error = InvalidQuizizzIdOrUrl("!")
            Assert.assertTrue(
                "Error message should contain <invalid id or url>",
                quiz.message.contains(error.message.toString(), ignoreCase = true)
            )
        }
    }

    @Test
    fun testInvalidQuizizzError() = runBlocking {
        val error = HttpException(
            Response.error<Any>(
                400,
                ResponseBody.create(
                    null,
                    ""
                )
            )
        )
        coEvery { api.extractQuizById("error") } throws error
        val quiz = repo.extractQuizById("error")
        Assert.assertTrue(quiz is ApiResponse.Error)
        if(quiz is ApiResponse.Error) {
            Assert.assertFalse(
                "Error message should not contain quizizz error: Received <${quiz.message}>",
                quiz.message.contains("quizizz error", ignoreCase = true)
            )
            Assert.assertTrue(
                "Error message should contain <Some error happened:> Received <${quiz.message}> ",
                quiz.message.contains("Some error happened", ignoreCase = true)
            )
        }
    }

}

