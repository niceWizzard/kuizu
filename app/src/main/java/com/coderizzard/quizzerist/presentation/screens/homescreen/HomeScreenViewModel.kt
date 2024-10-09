package com.coderizzard.quizzerist.presentation.screens.homescreen

import androidx.lifecycle.ViewModel
import com.coderizzard.database.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel  @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {


    val allQuizzes = quizRepository.getAll()
}


