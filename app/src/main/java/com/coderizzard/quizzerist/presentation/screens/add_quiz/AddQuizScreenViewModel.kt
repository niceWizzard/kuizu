package com.coderizzard.quizzerist.presentation.screens.add_quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.database.data.database.model.question.IdentificationQuestionEntity
import com.coderizzard.database.data.database.model.question.MultipleChoiceQuestionEntity
import com.coderizzard.database.data.database.model.question.QuestionEntity
import com.coderizzard.database.domain.repository.QuestionRepository
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.network.data.model.ExtractedIdentificationQuestion
import com.coderizzard.network.data.model.ExtractedMultipleChoiceQuestion
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
                            is ApiResponse.Error -> _searchQuiz.update { SearchQuizState.Error(res.message)}
                            is ApiResponse.Success -> {
                                _searchQuiz.update { SearchQuizState.Success(res.value) }
                                val extractedQuiz = res.value
                                val quizId = quizRepository.createQuiz(
                                    name = extractedQuiz.name,
                                    author = extractedQuiz.author,
                                    createdAt = extractedQuiz.createdAt,
                                    imageLink = extractedQuiz.imageLink,

                                )

                                extractedQuiz.questionList.forEach {
                                    questionRepository.createQuestion(
                                        when(it) {
                                            is ExtractedIdentificationQuestion -> IdentificationQuestionEntity(
                                                quizId = quizId,
                                                answer = it.answer,
                                                text = it.text,
                                                point = 1
                                            )
                                            is ExtractedMultipleChoiceQuestion -> MultipleChoiceQuestionEntity(
                                                quizId = quizId,
                                                point = 1,
                                                options = it.options,
                                                answer = it.answer,
                                                text = it.text
                                            )
                                        }
                                    )
                                }

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
    data class Success(val data : ExtractedQuiz) : SearchQuizState
    data object Fetching : SearchQuizState
    data class Invalid(val message : String) : SearchQuizState
}

sealed interface AddQuizEvent {
    data class OnSearchChange(val s : String ) : AddQuizEvent
    data object OnSearchSubmit : AddQuizEvent
}