package com.coderizzard.quiz.domain.repository

import android.content.Context
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.Question

interface ImageManager {
    suspend fun saveQuizImages(quiz : Quiz, context : Context) : Quiz
    suspend fun deleteImage(path: String) : Boolean
    suspend fun saveQuizQuestionImage(question: Question, context: Context): Question

}