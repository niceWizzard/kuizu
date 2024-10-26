package com.coderizzard.quiz.session.presentation.screen.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.database.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val quizRepository: QuizRepository,
) : ViewModel() {
    fun create(id: String) {
        viewModelScope.launch {
            delay(2000)
            _state.update { CreateState.Finished }
//            sessionRepository.createSession(id)
        }
    }

    private val _state = MutableStateFlow<CreateState>(CreateState.Creating)

    val state = _state.asStateFlow()
}


sealed interface CreateState {
    data object Creating : CreateState
    data class Error(val message : String) : CreateState
    data object Finished : CreateState
}