package com.revakovsky.thenytimesbooks.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens

@Composable
fun CoilImage(
    url: String,
    shouldRefreshImages: Boolean,
) {

    val context = LocalContext.current
    var showDefaultImage by remember { mutableStateOf(false) }

    if (shouldRefreshImages) showDefaultImage = false

    if (!showDefaultImage) {

        SubcomposeAsyncImage(
            model = url,
            contentDescription = context.getString(R.string.book_image),
            loading = {
                val state = painter.state

                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    if (state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(dimens.large))
                    }
                }
            },
            onError = { showDefaultImage = true },
            modifier = Modifier
                .width(dimens.bookImageWidth)
                .height(dimens.bookImageHeight),
            contentScale = ContentScale.Fit,
        )

    } else {

        Image(
            painter = painterResource(id = R.drawable.noimage),
            contentDescription = stringResource(id = R.string.image_is_not_available),
            modifier = Modifier
                .width(dimens.bookImageWidth)
                .height(dimens.bookImageHeight),
            contentScale = ContentScale.Fit,
        )

    }

}