package com.coderizzard.quiz.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.database.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class QuizScreenViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {
    private val _quizState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)

    val quizState = _quizState.asStateFlow()
    fun initialize(id : String) {
        viewModelScope.launch {
            quizRepository.getById(id).collect { quiz ->
                _quizState.update { QuizUiState.Success(quiz) }
            }
        }
    }
}

 sealed interface QuizUiState {
    data object Loading : QuizUiState
    data class Error(val msg : String) : QuizUiState
    data class Success(val quiz : Quiz) : QuizUiState
}