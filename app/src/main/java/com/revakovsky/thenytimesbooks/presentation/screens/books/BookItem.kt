package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.TextRegular
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle

@Composable
fun BookItem(
    category: BookUi,
    onBookItemClick: (categoryName: String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimens.medium)
            .clickable { onBookItemClick(category.bookTitle) },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        TextTitle(text = category.bookTitle)

        Row(
            modifier = Modifier.padding(top = dimens.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            TextRegular(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.book_author, category.author),
            )

            Spacer(modifier = Modifier.width(dimens.small))

            TextRegular(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.book_rank, category.rank),
            )

        }

    }

}