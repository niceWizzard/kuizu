package com.coderizzard.quiz.session.presentation.screen.session

import androidx.lifecycle.ViewModel
import com.coderizzard.core.data.navigation.NavigationManager
import com.coderizzard.core.data.navigation.RootRoute
import com.coderizzard.database.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    private val quizId = navigationManager.getRouteData<RootRoute.QuizSession>()?.id ?: throw Exception("Invalid Route")

    val sessionFlow = sessionRepository.getSessionFlow(quizId)

}