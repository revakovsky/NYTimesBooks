package com.revakovsky.thenytimesbooks.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val zero: Dp = 0.dp,
    val smallest: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp,
    val largest: Dp = 128.dp,

    val bookImageWidth: Dp = 200.dp,
    val bookImageHeight: Dp = 250.dp,
)

val LocalDimens = compositionLocalOf { Dimens() }

val dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current
