package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.revakovsky.thenytimesbooks.core.WindowType
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens

@Composable
fun BookItem(
    book: BookUi,
    shouldRefreshImages: Boolean,
    showDivider: Boolean,
    onButtonBuyClick: (bookTitle: String) -> Unit,
) {
    val windowType = WindowType.getWindowType()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.medium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        if (windowType == WindowType.Type.Small) {
            LayoutForSmallScreen(book, shouldRefreshImages, onButtonBuyClick)
        } else {
            LayoutForMediumAndLargerScreens(book, shouldRefreshImages, onButtonBuyClick)
        }

    }

    if (showDivider) Divider(
        modifier = Modifier.padding(horizontal = dimens.medium)
    )

}
