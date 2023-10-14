package com.revakovsky.thenytimesbooks.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextTitle(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textAlign: TextAlign = TextAlign.Start,
    singleLine: Boolean = true,
) {

    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = style.copy(textAlign = textAlign),
        color = textColor,
        maxLines = if (singleLine) 1 else 10,
        overflow = TextOverflow.Ellipsis
    )

}
