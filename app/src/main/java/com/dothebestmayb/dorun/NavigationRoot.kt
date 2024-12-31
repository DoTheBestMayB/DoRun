package com.dothebestmayb.dorun

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dothebestmayb.auth.presentation.intro.IntroScreenRoot
import com.dothebestmayb.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        startDestination = "auth",
        navController = navController,
    ) {
        authGraph(navController)
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
    }
}
