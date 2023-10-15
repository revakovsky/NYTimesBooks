package com.revakovsky.thenytimesbooks.presentation.screens.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.revakovsky.thenytimesbooks.core.WindowType
import com.revakovsky.thenytimesbooks.presentation.models.BookUi
import com.revakovsky.thenytimesbooks.presentation.ui.DevicePreviews
import com.revakovsky.thenytimesbooks.presentation.ui.theme.TheNYTimesBooksTheme
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens

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
            LayoutForSmallScreen(book, onBookItemClick)
        } else {
            LayoutForMediumAndLargerScreens(book, onBookItemClick)
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
                windowType = WindowType.Type.Small,
                onBookItemClick = { bookName -> }
            )
        }
    }
}
