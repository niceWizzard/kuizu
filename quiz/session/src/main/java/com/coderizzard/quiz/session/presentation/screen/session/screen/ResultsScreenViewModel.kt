package com.coderizzard.quiz.session.presentation.screen.session.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.ResultState
import com.coderizzard.core.data.AsyncData
import com.coderizzard.core.data.model.session.QuizSession
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.database.domain.repository.SessionRepository
import com.coderizzard.database.domain.repository.SessionResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val sessionResultRepository: SessionResultRepository,
) : ViewModel() {

    var sessionData by mutableStateOf<AsyncData<QuizSession>>(AsyncData.Loading)
    private set

    var resultData by mutableStateOf<AsyncData<SessionResult>>(AsyncData.Loading)
    private set


    fun initialize(quizId : String) {
        viewModelScope.launch {
           val res = sessionRepository.getSession(quizId)
            sessionData = when(res) {
                is ResultState.Error -> AsyncData.Error(res.message, res.exception)
                is ResultState.Success -> AsyncData.Success(res.data)
            }

            val sesResult = sessionResultRepository.getLatestResult(quizId)
            resultData = when(sesResult) {
                is ResultState.Error -> AsyncData.Error(sesResult.message, sesResult.exception)
                is ResultState.Success -> AsyncData.Success(sesResult.data)
            }

        }
    }

}