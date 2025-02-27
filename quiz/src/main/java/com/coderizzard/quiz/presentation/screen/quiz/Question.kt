package com.coderizzard.quiz.presentation.screen.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coderizzard.core.data.model.question.IdentificationQuestion
import com.coderizzard.core.data.model.question.MCQuestion
import com.coderizzard.core.data.model.question.Question
import com.coderizzard.core.data.model.question.UnsupportedQuestion
import com.coderizzard.core.data.toAnnotatedString
import com.coderizzard.core.presentation.expandable_image.ExpandableImage

@Composable
internal fun QuestionComp(q: Question, index : Int) {
    Card{
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${index+1}. ${q.getTypeName()}",
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "${q.point} pt",
                    fontWeight = FontWeight.Light
                )
            }
            HorizontalDivider()
            Spacer(Modifier.height(6.dp))
            if(q.imageLink.isNotBlank()) {
                ExpandableImage(
                    imageUrl = q.localImagePath,
                    contentDescription = "Image of question<${q.id}>",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
            Text(q.text.toAnnotatedString())
            Spacer(Modifier.height(6.dp))
            when (q) {
                is IdentificationQuestion -> {
                    Text("✅ ${q.answer.toAnnotatedString()}")
                }

                is MCQuestion -> {
                    q.options.mapIndexed { i, mcOption ->
                        Row {
                            Text(
                                if(q.answer.find { it == mcOption.remoteId } != null)
                                    "✅"
                                else "",
                                modifier = Modifier.widthIn(min=24.dp)
                            )
                            Text(mcOption.text.toAnnotatedString())
                        }

                    }
                }

                is UnsupportedQuestion -> {
                    Text("Type: ${q.type}")
                }
            }
        }
    }
}