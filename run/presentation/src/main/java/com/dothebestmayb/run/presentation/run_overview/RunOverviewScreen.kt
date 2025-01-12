@file:OptIn(ExperimentalMaterial3Api::class)

package com.dothebestmayb.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dothebestmayb.core.presentation.designsystem.AnalyticsIcon
import com.dothebestmayb.core.presentation.designsystem.DoRunTheme
import com.dothebestmayb.core.presentation.designsystem.LogoIcon
import com.dothebestmayb.core.presentation.designsystem.LogoutIcon
import com.dothebestmayb.core.presentation.designsystem.RunIcon
import com.dothebestmayb.core.presentation.designsystem.components.DoRunFloatingActionButton
import com.dothebestmayb.core.presentation.designsystem.components.DoRunScaffold
import com.dothebestmayb.core.presentation.designsystem.components.DoRunToolbar
import com.dothebestmayb.core.presentation.designsystem.components.util.DropDownItem
import com.dothebestmayb.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverViewScreenRoot(
    onStartRunClick: () -> Unit,
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverViewScreen(
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnStartClick -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RunOverViewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    DoRunScaffold(
        topAppBar = {
            DoRunToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.dorun),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp),
                    )
                }
            )
        },
        floatingActionButton = {
            DoRunFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverViewScreenPreview() {
    DoRunTheme {
        RunOverViewScreen(
            onAction = {}
        )
    }
}
