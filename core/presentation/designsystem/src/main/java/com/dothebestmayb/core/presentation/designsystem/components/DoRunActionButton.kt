package com.dothebestmayb.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dothebestmayb.core.presentation.designsystem.DorunBlack
import com.dothebestmayb.core.presentation.designsystem.DorunGray

@Composable
fun DoRunActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = DorunGray,
            disabledContainerColor = DorunBlack,
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier
            .height(IntrinsicSize.Min) // 하위 composable 내용에 따라 필요한 최소 공간만 차지하도록 설정
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(15.dp)
                .alpha(if (isLoading) 1f else 0f),
            strokeWidth = 1.5.dp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = text,
            // 로딩시 Text를 그리지 않고, 로딩 후에 그릴 경우 다른 UI가 밀리면서 갑자기 나타나는 것처럼 보인다.
            // 이것을 방지하기 위해 투명도를 조절해서 보이지 않도록 설
            modifier = Modifier
                .alpha(if (isLoading) 0f else 1f),
            fontWeight = FontWeight.Medium,
        )
        }
    }
}

@Composable
fun DoRunOutlinedActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton (
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier
            .height(IntrinsicSize.Min) // 하위 composable 내용에 따라 필요한 최소 공간만 차지하도록 설정
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = text,
                // 로딩시 Text를 그리지 않고, 로딩 후에 그릴 경우 다른 UI가 밀리면서 갑자기 나타나는 것처럼 보인다.
                // 이것을 방지하기 위해 투명도를 조절해서 보이지 않도록 설
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}