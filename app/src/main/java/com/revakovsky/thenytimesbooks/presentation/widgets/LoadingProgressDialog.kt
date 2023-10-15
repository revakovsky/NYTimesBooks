package com.revakovsky.thenytimesbooks.presentation.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens

@Composable
fun LoadingProgressDialog() {

    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {

        CircularProgressIndicator(
            modifier = Modifier.size(dimens.large),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.onPrimary,
        )

    }

}