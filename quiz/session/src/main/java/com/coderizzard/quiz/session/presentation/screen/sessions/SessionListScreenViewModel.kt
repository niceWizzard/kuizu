package com.coderizzard.quiz.session.presentation.screen.sessions

import androidx.lifecycle.ViewModel
import com.coderizzard.database.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionsScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    val sessionList = sessionRepository.getAll()
}

