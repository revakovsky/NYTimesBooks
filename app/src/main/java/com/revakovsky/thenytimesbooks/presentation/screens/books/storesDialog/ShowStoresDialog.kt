package com.revakovsky.thenytimesbooks.presentation.screens.books.storesDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.revakovsky.thenytimesbooks.navigation.DEFAULT_ANIMATION_DURATION
import com.revakovsky.thenytimesbooks.navigation.FADE_DURATION
import com.revakovsky.thenytimesbooks.presentation.models.StoreUi
import com.revakovsky.thenytimesbooks.presentation.ui.theme.dimens
import com.revakovsky.thenytimesbooks.presentation.widgets.TextTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShowStoresDialog(
    stores: List<StoreUi>,
    openChosenStore: (url: String) -> Unit,
    onDismiss: () -> Unit,
) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    var popUpAnimationVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { popUpAnimationVisibility = true }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                animateTheDialogHiding { popUpAnimationVisibility = false }
                onDismiss()
            }
        }
    ) {

        AnimatedVisibility(
            visible = popUpAnimationVisibility,
            enter = createSlideInEnterAnimation(),
            exit = createSlideOutExitAnimation()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.medium)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextTitle(
                    modifier = Modifier.padding(top = dimens.medium),
                    text = stringResource(R.string.select_a_store),
                    singleLine = false,
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
                                onItemClick = {
                                    coroutineScope.launch {
                                        animateTheDialogHiding {
                                            popUpAnimationVisibility = false
                                        }
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


private suspend fun animateTheDialogHiding(onHidingAction: () -> Unit) {
    onHidingAction()
    delay(DEFAULT_ANIMATION_DURATION.toLong())
}

@Composable
private fun createSlideInEnterAnimation() =
    slideInVertically(
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = FADE_DURATION * 2
        )
    )

@Composable
private fun createSlideOutExitAnimation() =
    slideOutVertically(
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutLinearInEasing
        ),
        targetOffsetY = { it / 6 }
    ) + fadeOut(
        animationSpec = tween(durationMillis = FADE_DURATION)
    )
