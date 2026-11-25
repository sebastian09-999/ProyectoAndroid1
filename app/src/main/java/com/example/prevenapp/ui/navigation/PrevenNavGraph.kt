package com.example.prevenapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prevenapp.ui.screens.*

@Composable
fun PrevenNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onEmpezarClick = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginProfesionalClick = {
                    navController.navigate(Screen.LoginProfesional.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onConfirmSuccess = {
                    navController.navigate(Screen.RegisterSuccess.route)
                },
                onConfirmError = { errorMsg ->
                    navController.navigate(Screen.RegisterError.createRoute(errorMsg))
                }
            )
        }

        composable(Screen.RegisterSuccess.route) {
            RegisterSuccessScreen(
                onContinueClick = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.RegisterError.route,
            arguments = listOf(navArgument("errorMessage") { type = NavType.StringType })
        ) { backStackEntry ->
            val errorMsg = backStackEntry.arguments?.getString("errorMessage") ?: ""
            RegisterErrorScreen(
                errorMessage = errorMsg,
                onBackToRegisterClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.LoginProfesional.route) {
            LoginProfesionalScreen(
                onLoginClick = {
                    // Navigate to home after login
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onRecuperarContrasenaClick = {
                    navController.navigate(Screen.PasswordRecoveryEmail.route)
                }
            )
        }

        composable(Screen.PasswordRecoveryEmail.route) {
            PasswordRecoveryEmailScreen(
                onNextClick = { _ ->
                    navController.navigate(Screen.PasswordRecoveryCode.route)
                },
                onNeedHelpClick = {
                    // Handle help action
                }
            )
        }

        composable(Screen.PasswordRecoveryCode.route) {
            PasswordRecoveryCodeScreen(
                onNextClick = { _ ->
                    navController.navigate(Screen.PasswordRecoveryNew.route)
                },
                onResendCodeClick = {
                    // Handle resend code action
                }
            )
        }

        composable(Screen.PasswordRecoveryNew.route) {
            PasswordRecoveryNewScreen(
                onNextClick = { _, _ ->
                    navController.navigate(Screen.PasswordRecoverySuccess.route)
                }
            )
        }

        composable(Screen.PasswordRecoverySuccess.route) {
            PasswordRecoverySuccessScreen(
                onBackToHomeClick = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
