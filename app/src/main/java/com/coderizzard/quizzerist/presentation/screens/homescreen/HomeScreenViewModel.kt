package com.coderizzard.quizzerist.presentation.screens.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.network.data.model.ExtractedQuiz
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.domain.ExtractedQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val extractorRepository : ExtractedQuizRepository
) : ViewModel() {

    private val _searchString  = mutableStateOf("")

    private val _searchQuiz = MutableStateFlow<SearchQuizState>(
        SearchQuizState.Default
    )

    val searchQuiz = _searchQuiz.asStateFlow()

    val searchString : State<String> = _searchString

    fun onEvent(event : HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnSearchSubmit -> {
                viewModelScope.launch {
                    val regex = Regex("""^((https?://)?(?:www\.)?quizizz\.com/.*|\w+)$""")
                    if(!regex.matches(_searchString.value)) {
                        _searchQuiz.update {
                            SearchQuizState.Invalid(
                                "Value must be either a quizizz url or a quizizz quiz id"
                            )
                        }
                    } else {

                        _searchQuiz.update { SearchQuizState.Fetching }
                        _searchQuiz.update{
                            when(val res = extractorRepository.extractQuizById(searchString.value)) {
                                is ApiResponse.Error -> {
                                    SearchQuizState.Error(res.message)
                                }
                                is ApiResponse.Success -> {
                                    SearchQuizState.Success(res.value)
                                }
                            }
                        }
                    }

                }
            }
            is HomeScreenEvent.OnSearchChange -> {
                _searchString.value = event.s

            }
        }
    }


}

sealed interface SearchQuizState {
    data object Default : SearchQuizState
    data class Error(val err : String ) : SearchQuizState
    data class Success(val data : ExtractedQuiz ) : SearchQuizState
    data object Fetching : SearchQuizState
    data class Invalid(val message : String) : SearchQuizState
}

sealed interface HomeScreenEvent {
    data class OnSearchChange(val s : String ) : HomeScreenEvent
    data object OnSearchSubmit : HomeScreenEvent
}
