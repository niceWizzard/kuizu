package com.coderizzard.quiz.presentation.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.database.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 internal class QuizScreenViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val navigationManager: NavigationManager,
) : ViewModel() {
    private val _quizState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    private var _routeParams : RootRoute.Quiz = navigationManager.getRouteData<RootRoute.Quiz>() ?:   throw Exception("Reached Quiz(#id) without a quiz route")

    init {
        viewModelScope.launch {
            quizRepository.getById(routeParams().id).collect { quiz ->
                _quizState.update { QuizUiState.Success(quiz) }
            }
        }
    }
    fun routeParams(): RootRoute.Quiz {
        return _routeParams
    }
    val quizState = _quizState.asStateFlow()
}



 internal sealed interface QuizUiState {
    data object Loading : QuizUiState
    data class Error(val msg : String) : QuizUiState
    data class Success(val quiz : Quiz) : QuizUiState
}