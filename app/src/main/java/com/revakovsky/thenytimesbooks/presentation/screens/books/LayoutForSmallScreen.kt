package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.AppButton
import com.revakovsky.thenytimesbooks.presentation.widgets.StylableText
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle

@Composable
fun LayoutForSmallScreen(
    book: BookUi,
    onBookItemClick: (bookTitle: String) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .width(dimens.bookImageWidth)
                .height(dimens.bookImageHeight),
            painter = painterResource(id = R.drawable.noimage),
            contentDescription = stringResource(R.string.book_image)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            TextTitle(
                modifier = Modifier.padding(top = dimens.medium),
                text = book.bookTitle,
                singleLine = false
            )

            StylableText(
                modifier = Modifier.padding(top = dimens.medium),
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

            StylableText(
                modifier = Modifier.padding(top = dimens.small),
                textToStyle = stringResource(
                    R.string.description,
                    book.description.ifEmpty {
                        stringResource(R.string.description_is_not_available)
                    }
                ),
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                AppButton(
                    modifier = Modifier.padding(top = dimens.large),
                    onButtonClick = { onBookItemClick(book.bookTitle) }
                )

            }

        }

    }

}