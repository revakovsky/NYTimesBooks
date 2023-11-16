package com.revakovsky.thenytimesbooks.presentation.screens.books.storesDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.revakovsky.thenytimesbooks.R
import com.revakovsky.thenytimesbooks.presentation.models.StoreUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DIALOG_APPEARANCE_TIME = 600
private const val DIALOG_FADE_TIME = 600

@Composable
fun ShowStoresDialog(
    stores: List<StoreUi>,
    openChosenStore: (url: String) -> Unit,
    onDismiss: () -> Unit,
) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    var showAnimatedAppearance by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { showAnimatedAppearance = true }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                showAnimatedAppearance = false
                delay(DIALOG_APPEARANCE_TIME.toLong())
                onDismiss()
            }
        }
    ) {

        AnimatedVisibility(
            visible = showAnimatedAppearance,
            enter = slideInEnterAnimation(),
            exit = slideOutExitAnimation()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.medium)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextTitle(
                    modifier = Modifier.padding(top = dimens.medium),
                    text = stringResource(R.string.select_a_store),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                LazyColumn(
                    modifier = Modifier.padding(top = dimens.large),
                    content = {
                        items(stores.size) { itemIndex ->
                            val store = stores[itemIndex]

                            DialogItem(
                                store = store,
                                showDivider = itemIndex != stores.lastIndex,
                                onStoreItemClick = {
                                    coroutineScope.launch {
                                        showAnimatedAppearance = false
                                        delay(DIALOG_APPEARANCE_TIME.toLong())
                                        onDismiss()
                                        openChosenStore(store.url)
                                    }
                                }
                            )

                        }
                    }
                )

            }

        }

    }

}

@Composable
private fun slideInEnterAnimation() =
    slideInVertically(
        animationSpec = tween(
            durationMillis = DIALOG_APPEARANCE_TIME,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(durationMillis = DIALOG_FADE_TIME * 2)
    )

@Composable
private fun slideOutExitAnimation() =
    slideOutVertically(
        animationSpec = tween(
            durationMillis = DIALOG_APPEARANCE_TIME,
            easing = FastOutSlowInEasing
        ),
        targetOffsetY = { it / 6 }
    ) + fadeOut(
        animationSpec = tween(durationMillis = DIALOG_FADE_TIME)
    )
