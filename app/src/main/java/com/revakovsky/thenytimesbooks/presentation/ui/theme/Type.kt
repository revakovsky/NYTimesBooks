package com.revakovsky.thenytimesbooks.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.revakovsky.thenytimesbooks.R

private val abhayaLibre = FontFamily(
    Font(R.font.abhayalibre_regular, weight = FontWeight.Normal),
    Font(R.font.abhayalibre_semibold, weight = FontWeight.W600),
    Font(R.font.abhayalibre_bold, weight = FontWeight.W800),
)

private val grenzegotich = FontFamily(
    Font(R.font.grenzegotisch_semibold, weight = FontWeight.W600),
)

val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = grenzegotich,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = abhayaLibre,
        fontWeight = FontWeight.W800,
        fontSize = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = abhayaLibre,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
)
