package com.cheesejuice.fancymansionsample.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cheesejuice.fancymansionsample.R

val NotoSansKRFontFamily = FontFamily(
    Font(R.font.notosans_kr_thin, FontWeight.Thin),
    Font(R.font.notosans_kr_light, FontWeight.Light),
    Font(R.font.notosans_kr_regular),
    Font(R.font.notosans_kr_medium, FontWeight.Medium),
    Font(R.font.notosans_kr_bold, FontWeight.Bold),
    Font(R.font.notosans_kr_black, FontWeight.Black)
)

val ReaderTitleStyle = TextStyle(
    fontFamily = NotoSansKRFontFamily,
    fontWeight = FontWeight.Medium,
    color = SlideButtonTextGray,
    letterSpacing = 0.5.sp,
    fontSize = 20.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val ReaderSubTitleStyle = TextStyle(
    fontFamily = NotoSansKRFontFamily,
    fontWeight = FontWeight.Medium,
    color = SlideButtonTextGray,
    letterSpacing = 0.5.sp,
    fontSize = 16.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)


val ReaderInfoStyle = TextStyle(
    fontFamily = NotoSansKRFontFamily,
    fontWeight = FontWeight.Normal,
    color = SlideButtonTextGray,
    letterSpacing = 0.5.sp,
    fontSize = 13.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val ReaderScriptStyle = TextStyle(
    fontFamily = NotoSansKRFontFamily,
    fontWeight = FontWeight.Normal,
    color = SlideButtonTextGray,
    letterSpacing = 0.5.sp,
    fontSize = 15.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val SlideButtonStyle = TextStyle(
    fontFamily = NotoSansKRFontFamily,
    fontWeight = FontWeight.Normal,
    color = SlideButtonTextGray,
    letterSpacing = 0.5.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NotoSansKRFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)