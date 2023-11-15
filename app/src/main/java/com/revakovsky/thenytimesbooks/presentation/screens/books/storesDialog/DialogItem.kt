package com.revakovsky.thenytimesbooks.presentation.screens.books.storesDialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revakovsky.thenytimesbooks.presentation.models.StoreUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle

@Composable
fun DialogItem(
    store: StoreUi,
    showDivider: Boolean,
    onStoreItemClick: () -> Unit,
) {

    Box(
        modifier = Modifier.clickable { onStoreItemClick() }
    ) {

        TextTitle(
            modifier = Modifier.padding(dimens.medium),
            text = store.storeName,
            singleLine = true
        )

    }

    if (showDivider) Divider(
        modifier = Modifier.padding(horizontal = dimens.medium)
    )

}
