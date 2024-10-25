package com.coderizzard.quiz.presentation.screen.quiz

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.coderizzard.core.data.model.Quiz
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.presentation.clickable_image.ClickableImage
import retrofit2.http.Header
import java.time.LocalDateTime

@Composable
fun QuizScreen() {
    val viewModel : QuizScreenViewModel = hiltViewModel()
    val quizRoute = viewModel.routeParams()
    val quiz by viewModel.quizState.collectAsState()
    when(quizRoute.id.trim()) {
        "" -> {
            Text("Invalid quiz id given.")
        }
        else -> {
            when(val state = quiz) {
                is QuizUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize() ,
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
                is QuizUiState.Error -> {
                    Text(state.msg)
                }

                is QuizUiState.Success -> {
                    QuizScreenContent(state.quiz)
                }
            }
        }
    }


}

@Composable
private fun QuizScreenContent(quiz: Quiz) {
    Surface(
        modifier =  Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(quiz)
            HorizontalDivider()
            if(quiz.questions.isEmpty()) {
                Text(
                    "Empty quiz...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            quiz.questions.mapIndexed {i,q ->
                QuestionComp(q,i)
            }
        }
    }
}

@Composable
private fun Header(quiz : Quiz) {
    Row(
        modifier = Modifier.fillMaxSize().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(
            18.dp, alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(quiz.localImagePath.isNotBlank()) {
            ClickableImage(
                modifier = Modifier.heightIn(min = 64.dp, max=128.dp),
                imageUrl = quiz.localImagePath,
                contentDescription = "Quiz image"
            )
        }
        Column(
        ) {
            Text(
                quiz.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                quiz.author,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 6.dp)
            )
            Text(
                "Created at: ${quiz.createdAt}",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val context = LocalContext.current
        TextButton(
            onClick = {
                Toast.makeText(context, "To be implemented", Toast.LENGTH_SHORT).show()
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites")
                Text("Favorite")
            }
        }
        TextButton(
            onClick = {
                Toast.makeText(context, "To be implemented", Toast.LENGTH_SHORT).show()
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Update")
                Text("Update")
            }
        }
        TextButton(
            onClick = {
                Toast.makeText(context, "To be implemented", Toast.LENGTH_SHORT).show()
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Create, contentDescription = "Tags")
                Text("Tags")
            }
        }
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    QuizScreenContent(
        Quiz(
            id = "lakjsdf",
            name = "Some Example quiz",
            author = "Example author",
            createdAt = LocalDateTime.now(),
            questions = listOf(
                IdentificationQuestion(
                    text = "lajksdf",
                    quizId = "lajksdf",
                    point = 1,
                    id = "klasfjdadsfiow",
                    answer = "lkajsdf",
                    remoteId = "lakjsdfqw"
                )
            ),
            remoteId = ""
        )
    )
}