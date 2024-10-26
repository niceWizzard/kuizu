package com.coderizzard.quiz.session.presentation.screen.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreateSessionScreen(
    id : String
) {
    val viewModel : CreateSessionViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.create(id)
    }
    Content(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(state: CreateState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        BasicAlertDialog(
            onDismissRequest = {}
        ) {
            Card {
                Box(
                    modifier = Modifier.padding(24.dp),
                    contentAlignment = Alignment.Center,
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement
                            .spacedBy(12.dp, alignment = Alignment.Start)
                    ) {
                        when(state) {
                            CreateState.Creating -> {
                                    CircularProgressIndicator()
                                    Text(
                                        "Creating...",
                                        fontSize = 18.sp,
                                    )
                            }
                            is CreateState.Error -> {
                                Text(state.message)
                            }
                            CreateState.Finished -> {
                                Text("Done creating session")
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun PreviewSessionScreen() {
    Content(
        state = CreateState.Creating
    )
}