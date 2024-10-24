package com.coderizzard.quiz.domain.repository

import android.content.Context
import com.coderizzard.core.data.model.Quiz

interface ImageManager {
    suspend fun saveQuizImages(quiz : Quiz, context : Context) : Quiz

}