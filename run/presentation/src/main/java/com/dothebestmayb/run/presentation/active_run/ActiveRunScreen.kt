@file:OptIn(ExperimentalMaterial3Api::class)

package com.dothebestmayb.run.presentation.active_run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.core.presentation.designsystem.DoRunTheme
import com.dothebestmayb.core.presentation.designsystem.StartIcon
import com.dothebestmayb.core.presentation.designsystem.StopIcon
import com.dothebestmayb.core.presentation.designsystem.components.DoRunFloatingActionButton
import com.dothebestmayb.core.presentation.designsystem.components.DoRunScaffold
import com.dothebestmayb.core.presentation.designsystem.components.DoRunToolbar
import com.dothebestmayb.run.presentation.R
import com.dothebestmayb.run.presentation.active_run.components.RunDataCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunEventScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunEventScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {
    DoRunScaffold(
        withGradient = false,
        topAppBar = {
            DoRunToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            DoRunFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    DoRunTheme {
        ActiveRunEventScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}