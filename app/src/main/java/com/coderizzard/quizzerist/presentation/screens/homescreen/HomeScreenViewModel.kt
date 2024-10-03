package com.coderizzard.quizzerist.presentation.screens.homescreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coderizzard.network.domain.ExtractedQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val extractorRepository : ExtractedQuizRepository
) : ViewModel() {
    val quizJson = MutableStateFlow("")
    init {
        viewModelScope.launch {
            quizJson.update {
                extractorRepository.extractQuizRaw("66fe0e65dfa2a58a6f1d0f4a")
            }
        }
    }
}