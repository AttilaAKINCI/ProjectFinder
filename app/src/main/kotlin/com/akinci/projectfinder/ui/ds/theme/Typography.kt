package com.akinci.projectfinder.ui.ds.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.akinci.projectfinder.R

private val Inter = FontFamily(
    Font(R.font.roboto_regular),
)

val textStyle = TextStyle(
    fontFamily = Inter,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
)

val AppTypography = Typography(
    displayLarge = textStyle.copy(
        lineHeight = 62.sp,
        fontSize = 56.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    displayMedium = textStyle.copy(
        lineHeight = 52.sp,
        fontSize = 45.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    displaySmall = textStyle.copy(
        lineHeight = 45.sp,
        fontSize = 39.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    headlineLarge = textStyle.copy(
        lineHeight = 40.sp,
        fontSize = 32.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    headlineMedium = textStyle.copy(
        lineHeight = 36.sp,
        fontSize = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    headlineSmall = textStyle.copy(
        lineHeight = 32.sp,
        fontSize = 26.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    titleLarge = textStyle.copy(
        lineHeight = 27.sp,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    titleMedium = textStyle.copy(
        lineHeight = 24.sp,
        fontSize = 17.sp,
        letterSpacing = 0.sp,
        fontWeight = W700,
    ),
    titleSmall = textStyle.copy(
        lineHeight = 22.sp,
        fontSize = 15.sp,
        letterSpacing = 0.sp,
        fontWeight = W400,
    ),
    labelLarge = textStyle.copy(
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        fontWeight = W400,
    ),
    labelMedium = textStyle.copy(
        lineHeight = 19.sp,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        fontWeight = W400,
    ),
    labelSmall = textStyle.copy(
        lineHeight = 16.sp,
        fontSize = 11.sp,
        letterSpacing = (0.5).sp,
        fontWeight = W400,
    ),
    bodyLarge = textStyle.copy(
        lineHeight = 24.sp,
        fontSize = 17.sp,
        letterSpacing = 0.sp,
        fontWeight = W400,
    ),
    bodyMedium = textStyle.copy(
        lineHeight = 22.sp,
        fontSize = 15.sp,
        letterSpacing = 0.sp,
        fontWeight = W400,
    ),
    bodySmall = textStyle.copy(
        lineHeight = 19.sp,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        fontWeight = W400,
    ),
)

val Typography.titleMediumBold: TextStyle
    get() = AppTypography.titleMedium.copy(
        fontWeight = W700,
    )
