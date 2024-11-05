package com.coderizzard.quiz.data

import android.content.Context
import android.util.Log
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.question.Question
import com.coderizzard.core.data.model.question.UnsupportedQuestion
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.quiz.domain.repository.ImageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

class ImageManagerImpl @Inject constructor(
    private val extractorRepository: ExtractedQuizRepository
) : ImageManager {
    override suspend fun saveQuizImages(quiz: Quiz, context: Context): Quiz = withContext(Dispatchers.IO) {
        quiz.copy(
            localImagePath =  if (quiz.imageLink.isNotBlank())
                    saveQuizMainImage(quiz.imageLink,quiz.remoteId, context)
                else "",
            allQuestions = quiz.questions.map { saveQuizQuestionImage(it,context) }

        )
    }

    override suspend fun deleteImage(path: String): Boolean {
        val file = File(path)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    override suspend fun saveQuizQuestionImage(question: Question, context: Context): Question {
        if(question.imageLink.isBlank()) {
            return question
        }
        return when (val resp = extractorRepository.extractImage(question.imageLink)) {
            is ApiResponse.Error -> {
                Log.e(
                    "AddQuizViewModel.saveImages",
                    "Couldn't extract the question image. \n${resp.message}"
                )
                question
            }
            is ApiResponse.Success -> {
                val questionImagePath = resp.value.body()?.byteStream()?.use { inputStream ->
                    saveImageToInternalStorage(
                        context,
                        inputStream,
                        "question-${question.remoteId}_${UUID.randomUUID()}.jpg"
                    )
                } ?: return question
                return when(question) {
                    is IdentificationQuestion -> {
                        question.copy(localImagePath = questionImagePath)
                    }
                    is MCQuestion -> {
                        question.copy(localImagePath = questionImagePath)
                    }

                    is UnsupportedQuestion -> {
                        question.copy(localImagePath = questionImagePath)
                    }
                }

            }
        }
    }

    private suspend fun saveQuizMainImage(imageLink: String, quizId : String,context: Context) : String {
        return when (val resp = extractorRepository.extractImage(imageLink)) {
            is ApiResponse.Error -> {
                Log.e(
                    "AddQuizViewModel.saveImages",
                    "Couldn't extract the quiz image. \n${resp.message}"
                )
                ""
            }

            is ApiResponse.Success -> {
                return resp.value.body()?.byteStream()?.use { inputStream ->
                    saveImageToInternalStorage(
                        context,
                        inputStream,
                        "quiz-${quizId}_${UUID.randomUUID()}.jpg"
                    )
                } ?: ""
            }
        }
    }

    private fun saveImageToInternalStorage(context: Context, inputStream: InputStream, fileName: String): String {
        val imagesDir = File(context.filesDir, "images")

        if (!imagesDir.exists()) {
            imagesDir.mkdir()
        }

        val imageFile = File(imagesDir, fileName)

        return try {
            imageFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }


}