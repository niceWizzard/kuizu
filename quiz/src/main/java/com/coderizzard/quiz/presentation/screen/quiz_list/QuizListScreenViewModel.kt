package com.coderizzard.quiz.presentation.screen.quiz_list

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
internal class QuizListScreenViewModel  @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            quizRepository.getAll().collect { list ->
                _allQuizzesFlow.update {
                    QuizListState.Success(list)
                }
            }
        }
    }
    private val _allQuizzesFlow = MutableStateFlow<QuizListState>(QuizListState.Loading)

    val allQuizzes = _allQuizzesFlow.asStateFlow()
}

internal sealed interface QuizListState {
    data object Loading : QuizListState
    data class Error(val message : String) : QuizListState
    data class Success(val data : List<Quiz>) : QuizListState
}

