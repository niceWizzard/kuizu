package com.coderizzard.quizzerist.presentation.screens.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.coderizzard.network.domain.ExtractedQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val extractorRepository : ExtractedQuizRepository
) : ViewModel() {

    private val _searchString  = mutableStateOf("")

    val searchString : State<String> = _searchString

    fun onEvent(event : HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnSearchSubmit -> {

            }
            is HomeScreenEvent.OnSearchChange -> {
                _searchString.value = event.s
            }
        }
    }


}

sealed interface HomeScreenEvent {
    data class OnSearchChange(val s : String ) : HomeScreenEvent
    data object OnSearchSubmit : HomeScreenEvent
}
