package com.coderizzard.quiz.session.presentation.screen.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.ResultState
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.question.Question
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.core.data.stripHtmlTags
import com.coderizzard.core.data.toAnnotatedString
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

    var sessionData  by mutableStateOf<AsyncData<QuizSession>>(AsyncData.Loading)
        private set

    private val _uiState = MutableStateFlow<SessionUiState>(SessionUiState.Default)
    val uiState = _uiState.asStateFlow()


    private fun getSession(): QuizSession {
        return (sessionData as AsyncData.Success).data
    }

    var toastMessage by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            sessionData = when(val res = sessionRepository.getSession(quizId)) {
                is ResultState.Error -> {
                    AsyncData.Error(res.message, res.exception)
                }

                is ResultState.Success -> {
                    AsyncData.Success(res.data)
                }
            }
        }
    }

    fun onEvent(e : ScreenEvent) {
        val session = getSession()
        val currentQuestion = session.getCurrentQuestion()
        when(e) {
            ScreenEvent.Start -> {
                val question = session.getCurrentQuestion()
                _uiState.update { SessionUiState.Answering(question) }
            }
            is ScreenEvent.MCAnswer -> {
                val question = (currentQuestion as MCQuestion)
                toastMessage = if(question.answer.any{e.answers.contains(it)}) {
                    "Correct"
                } else {
                    val answerString = question.answer.map { answer ->
                        val text = question.options.find {answer == it.id  }?.text ?: throw Exception("Invalid answer found.")
                        stripHtmlTags(text)
                    }
                    "Incorrect -> $answerString"
                }
                nextQuestion(session)
            }
            is ScreenEvent.IdentificationAnswer -> {
                val question = currentQuestion as IdentificationQuestion
                toastMessage = if(stripHtmlTags(question.answer).equals(e.answer, ignoreCase = true))
                    "Correct"
                else
                    "Incorrect -> ${question.answer}"
                nextQuestion(session)
            }
        }
    }

    private fun nextQuestion(session: QuizSession) {
        if (session.hasNextQuestion()) {
            val newSession = session.incrementQuestionIndex()
            sessionData = AsyncData.Success(newSession)
            _uiState.update { SessionUiState.Answering(newSession.getCurrentQuestion()) }
        } else {
            _uiState.update { SessionUiState.Finished }
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