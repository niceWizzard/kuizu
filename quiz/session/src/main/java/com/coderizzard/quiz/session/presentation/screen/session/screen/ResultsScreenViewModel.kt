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
import com.coderizzard.core.data.model.session.SessionResultWithUserAnswers
import com.coderizzard.core.data.model.session.answer.SessionAnswer
import com.coderizzard.database.domain.repository.SessionRepository
import com.coderizzard.database.domain.repository.SessionResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsScreenViewModel @Inject constructor(
    private val sessionResultRepository: SessionResultRepository,
) : ViewModel() {

    internal var data by mutableStateOf<AsyncData<SessionResultWithUserAnswers>>(AsyncData.Loading)
    private set


    fun initialize(quizId : String) {
        viewModelScope.launch {
            data = sessionResultRepository.getLatestResult(quizId).toAsyncData()
        }
    }

}

