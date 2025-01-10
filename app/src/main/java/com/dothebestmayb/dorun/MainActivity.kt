package com.dothebestmayb.dorun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dothebestmayb.core.presentation.designsystem.DoRunTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // 로그인 정보를 확인하는 동안 SplashScreen을 계속 보여줌
                viewModel.state.isCheckingAuth
            }
        }

        enableEdgeToEdge()
        setContent {
            DoRunTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    if (!viewModel.state.isCheckingAuth) {
                        NavigationRoot(
                            navController = navController,
                            isLoggedIn = viewModel.state.isLoggedIn,
                        )
                    }
                }
            }
        }
    }
}
