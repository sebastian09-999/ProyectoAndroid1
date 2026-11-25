package com.example.prevenapp.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Register : Screen("register")
    object RegisterSuccess : Screen("register_success")
    object RegisterError : Screen("register_error/{errorMessage}") {
        fun createRoute(errorMessage: String) = "register_error/$errorMessage"
    }
    object LoginProfesional : Screen("login_profesional")
    object PasswordRecoveryEmail : Screen("password_recovery_email")
    object PasswordRecoveryCode : Screen("password_recovery_code")
    object PasswordRecoveryNew : Screen("password_recovery_new")
    object PasswordRecoverySuccess : Screen("password_recovery_success")
}
