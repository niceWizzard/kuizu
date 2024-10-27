package com.coderizzard.quiz.session.presentation.screen.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.ResultState
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.question.Question
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.database.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    private val quizId = navigationManager.getRouteData<RootRoute.QuizSession>()?.id ?: throw Exception("Invalid Route")

    private val _sessionData = MutableStateFlow<AsyncData<QuizSession>>(AsyncData.Loading)
    private val _uiState = MutableStateFlow<SessionUiState>(SessionUiState.Default)
    val uiState = _uiState.asStateFlow()
    val sessionData = _sessionData.asStateFlow()

    private lateinit var  session : QuizSession

    init {
        viewModelScope.launch {
            when(val res = sessionRepository.getSession(quizId)) {
                is ResultState.Error -> {
                    _sessionData.update { AsyncData.Error(res.message, res.exception) }
                }
                is ResultState.Success -> {
                    _sessionData.update { AsyncData.Success(res.data) }
                    session = res.data
                }
            }
        }
    }

    fun onEvent(e : ScreenEvent) {
        when(e) {
            ScreenEvent.Start -> {
                val question = session.getCurrentQuestion()
                _uiState.update { SessionUiState.Answering(question) }
            }
            is ScreenEvent.MCAnswer,
            is ScreenEvent.IdentificationAnswer -> {
                session = session.incrementQuestionIndex()
                _uiState.update { SessionUiState.Answering(session.getCurrentQuestion()) }
            }
        }
    }

}

sealed interface ScreenEvent {
    data object Start : ScreenEvent
    data class MCAnswer(val answers : List<String>) : ScreenEvent
    data class IdentificationAnswer(val answer : String) : ScreenEvent
}

sealed interface SessionUiState {
    data object Default : SessionUiState
    data class Answering(val q : Question) : SessionUiState
    data object Finished : SessionUiState
}