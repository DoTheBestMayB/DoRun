package com.dothebestmayb.auth.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dothebestmayb.auth.presentation.R
import com.dothebestmayb.core.presentation.designsystem.DoRunTheme
import com.dothebestmayb.core.presentation.designsystem.LogoIcon
import com.dothebestmayb.core.presentation.designsystem.components.DoRunActionButton
import com.dothebestmayb.core.presentation.designsystem.components.DoRunOutlinedActionButton
import com.dothebestmayb.core.presentation.designsystem.components.GradientBackground

/**
 * XxxRoot Composable을 별도로 만드는 이유는 테스트, Preview, DI 흐름 때문이다.
 * 1. composable 테스트를 작성할 때, viewModel을 파라미터로 입력하면 viewModel을 별도로 처리해줘야 한다.
 * 2. Preview는 ViewModel에 있는 데이터 등을 반영해서 보여주지 않는다.
 * 3. 이 Composable에 ViewModel을 주입하기 위해 상위 Composable 들도 해당 ViewModel을 주입받아야 한다.
 *
 * 그래서 ViewModel, NavController를 주입하는 XxxRoot Composable을 만들고
 * event, state를 주입받는 Composable을 호출하도록 구성한다.
 *
 * 멀티 모듈 프로젝트에서 feature module composable에 navController를 주입하는 것은 좋지 않다.
 * 재사용 가능한 것이 멀티 모듈의 장점인데, navController를 주입받아 feature 모듈이 다른 feature 모듈에 종속되기 때문이다.
 * 예를 들어, 로그인을 완료한 후 RunScreen으로 이동하도록 하면, Running이 아닌 다른 앱에서는 재활용할 수 없는 것이다.
 *
 * 그래서 여기서는 NavController 대신 람다 action을 주입받고,
 * app 모듈에서 각 action에 해당하는 NavController action을 람다로 주입한다.
 */
@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        }
    )
}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit,
) {
    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // 버튼 영역을 제외한 나머지 공간을 차지하도록 하기 위함
            contentAlignment = Alignment.Center,
        ) {
            DoRunLogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_to_dorun),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.dorun_description),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(32.dp))
            DoRunOutlinedActionButton (
                text = stringResource(id = R.string.sign_in),
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignInClick)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            DoRunActionButton(
                text = stringResource(id = R.string.sign_up),
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignUpClick)
                }
            )
        }
    }
}

@Composable
private fun DoRunLogoVertical(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.dorun),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    DoRunTheme {
        IntroScreen(
            onAction = {},
        )
    }
}