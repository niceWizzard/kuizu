package com.coderizzard.quizzerist.presentation.screens.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.network.data.model.ExtractedQuiz
import com.coderizzard.network.data.repository.ApiResponse
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
    val quizJson = MutableStateFlow<ExtractedQuiz?>(null)
    init {
        viewModelScope.launch {
            when(val res = extractorRepository.extractQuizById("66fe0e65dfa2a58a6f1d0f4a")) {
                is ApiResponse.Success -> {
                    quizJson.update {
                        res.value
                    }
                }
                is ApiResponse.Error -> {
                    Log.e("E", res.message)
                }

            }

        }
    }
}