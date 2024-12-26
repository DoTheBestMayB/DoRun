package com.dothebestmayb.core.presentation.designsystem.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.core.presentation.designsystem.DoRunTheme


@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    hasToolbar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // roundToPx 함수는 Density interface 내부에서 Dp를 수신객체로 지정한 확장함수다.
    // 따라서 density를 scope function으로 감싸서 호출할 수 밖에 없다.
    // 이렇게 구현된 이유는 Px로 변환할 때, LocalDensity에 있는 density 값을 사용하도록 강제하기 위함으로 추측된다.
    // Density 구현체는 아래에서 확인할 수 있다.
    // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui-unit/src/androidMain/kotlin/androidx/compose/ui/unit/AndroidDensity.android.kt?q=Density&ss=androidx%2Fplatform%2Fframeworks%2Fsupport
    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }
    // 화면의 가로, 세로 중 더 작은 길이를 선택 -> 화면 회전 고
    val smallDimension = minOf(
        configuration.screenWidthDp.dp,
        configuration.screenHeightDp.dp,
    )
    val smallDimensionPx = with(density) {
        smallDimension.roundToPx()
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val isAtLeastAndroid12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .then(
                    if (isAtLeastAndroid12) {
                        Modifier.blur(smallDimension / 3f)
                    } else Modifier
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            if (isAtLeastAndroid12) primaryColor else primaryColor.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(
                            x = screenWidthPx / 2f,
                            y = -100f
                        ),
                        radius = smallDimensionPx / 2f, // 가로 모드시 영향을 줌
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (hasToolbar) {
                        Modifier
                    } else {
                        Modifier.systemBarsPadding()
                    }
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun GradientBackgroundPreview() {
    DoRunTheme {
        GradientBackground(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}