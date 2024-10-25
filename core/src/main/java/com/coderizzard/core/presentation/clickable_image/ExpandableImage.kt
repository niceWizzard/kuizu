package com.coderizzard.core.presentation.clickable_image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableImage(
    imageUrl : String,
    modifier: Modifier = Modifier,
    contentDescription : String,
    viewModel: ClickableImageViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val isModalShown by viewModel.isModalShown.collectAsState()
    val model = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()
    SubcomposeAsyncImage(
        model = model,
        contentDescription= contentDescription,
        modifier = modifier.clickable {
            viewModel.showModal()
        },
        loading = {
            CircularProgressIndicator()
        },
        error = {
            Text(
                it.result.toString(),
                color = MaterialTheme.colorScheme.error
            )
        }
    )
    if(isModalShown) {
        BasicAlertDialog(
            onDismissRequest = {
                viewModel.hideModal()
            }
        ) {
            SubcomposeAsyncImage(
                model = model,
                contentDescription= contentDescription,
                modifier = Modifier.fillMaxWidth(),
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Text(
                        it.result.toString(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            )
        }
    }
}