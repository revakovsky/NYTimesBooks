package com.revakovsky.thenytimesbooks.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(
    titleText: String = "",
    showNavigationIcon: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationIconClick: () -> Unit = { },
) {

    TopAppBar(
        title = {
            if (titleText.isNotEmpty()) Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = dimens.medium),
                text = titleText,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            if (showNavigationIcon) IconButton(onClick = { onNavigationIconClick() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
        scrollBehavior = scrollBehavior
    )

}