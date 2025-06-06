package com.dothebestmayb.dorun

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.dothebestmayb.auth.presentation.intro.IntroScreenRoot
import com.dothebestmayb.auth.presentation.login.LoginScreenRoot
import com.dothebestmayb.auth.presentation.register.RegisterScreenRoot
import com.dothebestmayb.run.presentation.active_run.ActiveRunScreenRoot
import com.dothebestmayb.run.presentation.active_run.service.ActiveRunService
import com.dothebestmayb.run.presentation.run_overview.RunOverViewScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    NavHost(
        startDestination = if (isLoggedIn) "run" else "auth",
        navController = navController,
    ) {
        authGraph(navController)
        runGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            // IntroScreenRoot에서 Navigation 구현을 parent에게 넘김으로써
            // app 모듈이 해당 책임을 지게 됐고, destination을 자유롭게 설정할 수 있게 됨
            // 예를 들어 Feature module을 Running앱이 아닌 다른 앱에서 사용했다면 목적지를 다르게 설정해서 사용하면 된다.
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(navController: NavController) {
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable("run_overview") {
            RunOverViewScreenRoot(
                onStartRunClick = {
                    navController.navigate("active_run")
                }
            )
        }
        composable(
            route = "active_run",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "dorun://active_run"
                }
            )
        ) {
            val context = LocalContext.current

            ActiveRunScreenRoot(
                onServiceToggle = { shouldServiceRun ->
                    if (shouldServiceRun) {
                        context.startService(
                            ActiveRunService.createStartIntent(
                                context = context,
                                activityClass = MainActivity::class.java,
                            )
                        )
                    } else {
                        // 여기서 startService는 새로운 Service를 또 시작하는 게 아니라, 기존 Service에 Intent를 전달함
                        context.startService(
                            ActiveRunService.createStopIntent(
                                context = context,
                            )
                        )
                    }
                }
            )
        }
    }
}
