package com.dothebestmayb.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = DorunGreen, // 강조되는 주요 색상
    background = DorunBlack, // 배경 색상
    surface = DorunDarkGray, // Dialog와 같은 곳에 사용되는 배경 색상(기본 배경 색상과 구분 필요)
    secondary = DorunWhite,
    tertiary = DorunWhite,
    primaryContainer = DorunGreen30, // floating action button과 같은 색상?
    onPrimary = DorunBlack, // Primary 색상 위에 그려지는 아이템의 색상
    onBackground = DorunWhite,
    onSurface = DorunWhite,
    onSurfaceVariant = DorunGray,
    error = DorunDarkRed,
    errorContainer = DorunDarkRed5,
)

// darkTheme, dynamicColor는 지원하지 않으므로 삭제함
@Composable
fun DoRunTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // statusbar를 투명하게 만드는 코드
//            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
