package com.coderizzard.quiz.data

import android.content.Context
import android.util.Log
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.quiz.domain.repository.ImageManager
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

class ImageManagerImpl @Inject constructor(
    private val extractorRepository: ExtractedQuizRepository
) : ImageManager {
    override suspend fun saveQuizImages(quiz: Quiz, context: Context): Quiz {
        var newQuiz = quiz
        if (quiz.imageLink.isNotBlank()) {
            newQuiz = saveQuizMainImage(quiz, context)
        }
        return newQuiz
    }

    override suspend fun deleteImage(path: String): Boolean {
        val file = File(path)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    private suspend fun saveQuizMainImage(quiz: Quiz, context: Context) : Quiz {
        return when (val resp = extractorRepository.extractImage(quiz.imageLink)) {
            is ApiResponse.Error -> {
                Log.e(
                    "AddQuizViewModel.saveImages",
                    "Couldn't extract the quiz image. \n${resp.message}"
                )
                quiz
            }

            is ApiResponse.Success -> {
                val quizImagePath = resp.value.body()?.byteStream()?.use { inputStream ->
                    saveImageToInternalStorage(
                        context,
                        inputStream,
                        "quiz-${quiz.remoteId}_${UUID.randomUUID()}.jpg"
                    )
                } ?: return quiz
                return quiz.copy(localImagePath = quizImagePath)
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