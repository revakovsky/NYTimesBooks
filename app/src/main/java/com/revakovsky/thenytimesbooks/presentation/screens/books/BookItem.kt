package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.core.WindowType
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.ui.DevicePreviews
import com.revakovsky.thenytimesbooks.presentation.ui.theme.TheNYTimesBooksTheme
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.AppButton
import com.revakovsky.thenytimesbooks.presentation.widgets.StylableText
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: BookUi,
    windowType: WindowType.Type,
    onBookItemClick: (bookTitle: String) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimens.medium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        if (windowType == WindowType.Type.Small) {

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                // TODO: create a layout for the small type of the screen

            }

        } else {

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

                    Image(
                        modifier = Modifier
                            .width(dimens.bookImageWidth)
                            .height(dimens.bookImageHeight),
                        painter = painterResource(id = R.drawable.noimage),
                        contentDescription = stringResource(R.string.book_image)
                    )

                    Column(
                        modifier = modifier
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

    }

}

@Composable
@DevicePreviews
fun PreviewBookItem() {
    TheNYTimesBooksTheme {
        Surface {
            BookItem(
                book = BookUi(
                    bookTitle = "Dagger 2 and Jetpack Compose Integration with Help",
                    author = "Android Google",
                    description = "Highlighting the advantages of DI is not the purpose of this article, but almost all of the project needs it. In the official documentation, it’s easy to learn how to use Hilt with Jetpack Compose; however, in the real world, most of us have been using Dagger 2 for dependency injections.",
                    rank = 5,
                    publisher = "Revakovskyi publisher compane Tanaya and Maxim"
                ),
                windowType = WindowType.Type.Medium,
                onBookItemClick = { bookName -> }
            )
        }
    }
}
