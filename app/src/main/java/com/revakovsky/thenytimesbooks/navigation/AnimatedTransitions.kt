package com.revakovsky.thenytimesbooks.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val DEFAULT_ANIMATION_DURATION = 600
private const val FADE_DURATION = 800

fun NavGraphBuilder.composableWithAnimatedTransition(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    enterAndExitVertically: Boolean = false,
    content: @Composable (AnimatedContentScope, NavBackStackEntry) -> Unit,
) {

    composable(
        route = route,
        enterTransition = {
            if (enterAndExitVertically) enterFromBottomToTop()
            else enterFromRightToLeft()
        },
        exitTransition = { exitFromRightToLeft() },
        popEnterTransition = { popEnterFromLeftToRight() },
        popExitTransition = {
            if (enterAndExitVertically) exitFromTopToBottom()
            else popExitFromLeftToRight()
        },
        arguments = arguments
    ) {
        content(this, it)
    }

}


fun AnimatedContentTransitionScope<NavBackStackEntry>.enterFromRightToLeft() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = FADE_DURATION,
            easing = FastOutSlowInEasing
        )
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.exitFromRightToLeft() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutLinearInEasing
        ),
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = FADE_DURATION,
            easing = FastOutLinearInEasing
        )
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterFromLeftToRight() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = FADE_DURATION,
            easing = FastOutSlowInEasing
        )
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitFromLeftToRight() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutLinearInEasing
        ),
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = FADE_DURATION,
            easing = FastOutLinearInEasing
        )
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.enterFromBottomToTop() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = FADE_DURATION,
            easing = FastOutSlowInEasing
        )
    )


fun AnimatedContentTransitionScope<NavBackStackEntry>.exitFromTopToBottom() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutLinearInEasing
        ),
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = FADE_DURATION,
            easing = FastOutLinearInEasing
        )
    )