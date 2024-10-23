package com.coderizzard.quiz.data

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.domain.ExtractedQuizRepository
import com.coderizzard.quiz.domain.repository.ImageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ImageManagerImpl @Inject constructor(
    private val extractorRepository: ExtractedQuizRepository
) : ImageManager {
    override suspend fun saveQuizImages(quiz: Quiz, context: Context) {
        if (quiz.imageLink.isNotBlank()) {
            saveQuizMainImage(quiz, context)
        }
    }

    private suspend fun saveQuizMainImage(quiz: Quiz, context: Context) {
        when (val resp = extractorRepository.extractImage(quiz.imageLink)) {
            is ApiResponse.Error -> {
                Log.e(
                    "AddQuizViewModel.saveImages",
                    "Couldn't extract the quiz image. \n${resp.message}"
                )
            }
            is ApiResponse.Success -> {
                resp.value.body()?.byteStream()?.let { inputStream ->
                    saveImageToStorage(context, inputStream)
                }
            }
        }
    }


    private suspend fun saveImageToStorage(context : Context, stream : InputStream) {
        val fileName = SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS",
            Locale.ENGLISH
        ).format(System.currentTimeMillis())
    }


}