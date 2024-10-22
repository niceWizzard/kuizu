package com.coderizzard.quiz.presentation.screen.add_quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MCQuestionEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.network.data.repository.ApiResponse
import com.coderizzard.network.domain.ExtractedQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizScreenViewModel@Inject constructor(
    private val extractorRepository : ExtractedQuizRepository,
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
) : ViewModel() {
    private val _searchString  = mutableStateOf("")

    private val _searchQuiz = MutableStateFlow<SearchQuizState>(
        SearchQuizState.Default
    )

    val searchQuiz = _searchQuiz.asStateFlow()

    val searchString : State<String> = _searchString

    fun onEvent(event : AddQuizEvent) {
        when(event) {
            is AddQuizEvent.OnSearchSubmit -> {
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
                        when(val res = extractorRepository.extractQuizById(searchString.value)) {
                            is ApiResponse.Error -> _searchQuiz.update { SearchQuizState.Error(res.message) }
                            is ApiResponse.Success -> {
                                _searchQuiz.update { SearchQuizState.Success(res.value) }
                                val extractedQuiz = res.value
                                quizRepository.createQuiz(
                                    extractedQuiz
                                )
                            }
                        }
                    }

                }
            }
            is AddQuizEvent.OnSearchChange -> {
                _searchString.value = event.s

            }
        }
    }
}

sealed interface SearchQuizState {
    data object Default : SearchQuizState
    data class Error(val err : String ) : SearchQuizState
    data class Success(val data : Quiz) : SearchQuizState
    data object Fetching : SearchQuizState
    data class Invalid(val message : String) : SearchQuizState
}

sealed interface AddQuizEvent {
    data class OnSearchChange(val s : String ) : AddQuizEvent
    data object OnSearchSubmit : AddQuizEvent
}