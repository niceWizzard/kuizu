package com.coderizzard.core.presentation.clickable_image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableImage(
    imageUrl : String,
    modifier: Modifier = Modifier,
    contentDescription : String,
) {
    var isModalShown by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val model = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()
    SubcomposeAsyncImage(
        model = model,
        contentDescription= contentDescription,
        modifier = modifier.clickable {
            isModalShown = true
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
                isModalShown = false
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