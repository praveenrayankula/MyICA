package uk.ac.tees.w9623647.parkme.login

sealed class Screen(val route: String) {
    data object Login: Screen(route = "login_screen")
    data object Register: Screen(route = "register_screen")
    data object ForgotPassword: Screen(route = "forgot_password_screen")
    data object HomeScreen: Screen(route = "home_screen")
}
