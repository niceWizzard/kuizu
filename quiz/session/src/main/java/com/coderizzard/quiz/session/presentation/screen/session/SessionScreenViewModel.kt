package com.coderizzard.quiz.session.presentation.screen.session

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import kotlinx.coroutines.delay
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

    var currentScore by mutableIntStateOf(0)
    private set

    var answeringState by mutableStateOf<AnsweringState>(AnsweringState.Unanswered)
    private set

    private fun getSession(): QuizSession {
        return (sessionData as AsyncData.Success).data
    }

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
                val question = when(val q = session.getCurrentQuestion()) {
                    is IdentificationQuestion -> q
                    is MCQuestion -> {q.toShuffledOptions()}
                }
                _uiState.update { SessionUiState.Answering(question) }
            }
            is ScreenEvent.MCAnswer -> {
                val question = (currentQuestion as MCQuestion)
                if(question.answer.any{e.answers.contains(it)}) {
                    answeringState = AnsweringState.Correct
                    currentScore++
                } else {
                    answeringState = AnsweringState.IncorrectMCAnswer(e.answers)
                }
                nextQuestion(session)
            }
            is ScreenEvent.IdentificationAnswer -> {
                val question = currentQuestion as IdentificationQuestion
                if(stripHtmlTags(question.answer).equals(e.answer.trim(), ignoreCase = true)) {
                    answeringState = AnsweringState.Correct
                    currentScore++
                } else {
                    answeringState = AnsweringState.IncorrectIdentificationAnswer(e.answer)
                }
                nextQuestion(session)
            }
        }
    }

    private fun nextQuestion(session: QuizSession) {
        viewModelScope.launch {
            delay(1500)
            answeringState = AnsweringState.Unanswered
            if (session.hasNextQuestion()) {
                val newSession = session.incrementQuestionIndex()
                sessionData = AsyncData.Success(newSession)
                val currentQuestion = when(val q = newSession.getCurrentQuestion()) {
                    is IdentificationQuestion -> q
                    is MCQuestion -> q.toShuffledOptions()
                }
                _uiState.update { SessionUiState.Answering(currentQuestion) }
            } else {
                _uiState.update { SessionUiState.Finished }
            }
        }
    }

}

sealed interface AnsweringState {
    data object Unanswered : AnsweringState
    data object Correct:  AnsweringState
    data class IncorrectMCAnswer(val answers : List<String> ) : AnsweringState
    data class IncorrectIdentificationAnswer(val answers : String ) : AnsweringState
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