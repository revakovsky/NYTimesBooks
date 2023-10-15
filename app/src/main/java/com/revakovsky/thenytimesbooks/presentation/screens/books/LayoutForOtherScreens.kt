package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.AppButton
import com.revakovsky.thenytimesbooks.presentation.widgets.CoilImage
import com.revakovsky.thenytimesbooks.presentation.widgets.StylableText
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle

@Composable
fun LayoutForMediumAndLargerScreens(
    book: BookUi,
    shouldRefreshImages: Boolean,
    onBookItemClick: (bookTitle: String) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {

            CoilImage(url = book.image, shouldRefreshImages)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimens.medium),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                TextTitle(
                    text = book.bookTitle,
                    singleLine = false
                )

                StylableText(
                    modifier = Modifier.padding(top = dimens.large),
                    textToStyle = stringResource(R.string.book_rank, book.rank)
                )

                StylableText(
                    modifier = Modifier.padding(top = dimens.small),
                    textToStyle = stringResource(R.string.book_author, book.author),
                )

                StylableText(
                    modifier = Modifier.padding(top = dimens.small),
                    textToStyle = stringResource(R.string.publisher, book.publisher),
                )

                AppButton(
                    modifier = Modifier.padding(top = dimens.large),
                    onButtonClick = { onBookItemClick(book.bookTitle) }
                )

            }

        }

        StylableText(
            modifier = Modifier.padding(top = dimens.large),
            textToStyle = stringResource(
                R.string.description,
                book.description.ifEmpty {
                    stringResource(R.string.description_is_not_available)
                }
            ),
        )

    }

}
