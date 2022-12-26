package levilin.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import levilin.todocompose.ui.SplashScreen
import levilin.todocompose.utility.ConstantValue

fun NavGraphBuilder.splashComposable(navigateToListScreen: () -> Unit) {
    composable(route = ConstantValue.SPLASH_SCREEN) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}