package com.coderizzard.quiz.session.presentation.screen.session

import android.util.Log
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
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.core.data.model.session.answer.IdentificationAnswer
import com.coderizzard.core.data.model.session.answer.MCQuestionAnswer
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.core.data.stripHtmlTags
import com.coderizzard.core.data.toAnnotatedString
import com.coderizzard.database.domain.repository.SessionRepository
import com.coderizzard.database.domain.repository.SessionResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val navigationManager: NavigationManager,
    private val sessionResultRepository: SessionResultRepository,
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

    var isQuestionVisible by mutableStateOf(false)
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
        when(e) {
            ScreenEvent.Start -> {
                if(session.isFinished()) {
                    _uiState.update { SessionUiState.Results }
                } else {
                    val question = when(val q = session.getCurrentQuestion()) {
                        is IdentificationQuestion -> q
                        is MCQuestion -> {q.toShuffledOptions()}
                    }
                    viewModelScope.launch {
                        isQuestionVisible = true
                        delay(500)
                        _uiState.update { SessionUiState.Answering(question) }
                    }
                }
            }
            is ScreenEvent.MCAnswer -> {
                val question = (session.getCurrentQuestion() as MCQuestion)
                val isCorrect =question.answer.any{e.answers.contains(it)}
                if(isCorrect) {
                    answeringState = AnsweringState.Correct
                    currentScore++
                } else {
                    answeringState = AnsweringState.IncorrectMCAnswer(e.answers)
                }
                viewModelScope.launch {
                    sessionRepository.createQuestionAnswer(
                        MCQuestionAnswer(
                            quizId = session.quizId,
                            isCorrect = isCorrect,
                            correctAnswerIds = e.answers,
                            questionId = question.id,
                        )
                    )
                }
                nextQuestion(session)
            }
            is ScreenEvent.IdentificationAnswer -> {
                val question = session.getCurrentQuestion() as IdentificationQuestion
                val isCorrect = stripHtmlTags(question.answer).equals(e.answer.trim(), ignoreCase = true)
                if(isCorrect) {
                    answeringState = AnsweringState.Correct
                    currentScore++
                } else {
                    answeringState = AnsweringState.IncorrectIdentificationAnswer(e.answer)
                }
                viewModelScope.launch {
                    sessionRepository.createQuestionAnswer(
                        IdentificationAnswer(
                            quizId=session.quizId,
                            questionId = question.id,
                            isCorrect =isCorrect,
                            correctAnswer = e.answer
                        )
                    )
                }
                nextQuestion(session)
            }
        }
    }

    private fun nextQuestion(session: QuizSession) {
        viewModelScope.launch {
            delay(1500)
            isQuestionVisible = false
            delay(500)

            answeringState = AnsweringState.Unanswered
            if (session.hasNextQuestion()) {
                val newSession = session.incrementQuestionIndex()
                sessionData = AsyncData.Success(newSession)
                val currentQuestion = when(val q = newSession.getCurrentQuestion()) {
                    is IdentificationQuestion -> q
                    is MCQuestion -> q.toShuffledOptions()
                }
                _uiState.update { SessionUiState.Answering(currentQuestion) }
                isQuestionVisible = true
                delay(500)
            } else {
                _uiState.update { SessionUiState.Finished }

                sessionResultRepository.createResult(
                    SessionResult(
                        quizId = session.quizId,
                        dateFinished = LocalDateTime.now(),
                        marks = currentScore,
                        totalPoints = session.questionOrder.size,
                    )
                )
                _uiState.update { SessionUiState.Results }

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
    data object Results : SessionUiState
}