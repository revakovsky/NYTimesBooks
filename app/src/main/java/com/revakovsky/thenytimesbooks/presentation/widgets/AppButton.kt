package com.revakovsky.thenytimesbooks.presentation.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    buttonText: String = stringResource(id = R.string.buy),
    onButtonClick: () -> Unit,
) {

    Button(
        modifier = modifier.width(100.dp),
        onClick = onButtonClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = RoundedCornerShape(100),
        contentPadding = PaddingValues(dimens.small),
    ) {

        TextRegular(
            text = buttonText,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

    }

}