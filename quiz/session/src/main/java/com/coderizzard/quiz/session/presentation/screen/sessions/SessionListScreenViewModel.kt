package com.coderizzard.quiz.session.presentation.screen.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.database.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionsScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    val navigationManager: NavigationManager,
) : ViewModel() {

    val sessionList = sessionRepository.getAll()

    fun onEvent(e : ScreenEvent) {
        when(e){
            is ScreenEvent.OnDelete -> {
                viewModelScope.launch {
                    sessionRepository.deleteSession(e.quizId)
                }
            }
        }
    }

}

sealed interface ScreenEvent {
    data class OnDelete(val quizId : String) : ScreenEvent
}

