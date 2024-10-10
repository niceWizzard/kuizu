package com.coderizzard.quiz.presentation.screen.quiz_list

import androidx.lifecycle.ViewModel
import com.coderizzard.database.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizListScreenViewModel  @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {


    val allQuizzes = quizRepository.getAll()
}


