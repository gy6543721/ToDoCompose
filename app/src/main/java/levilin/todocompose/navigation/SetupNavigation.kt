package levilin.todocompose.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import levilin.todocompose.navigation.destination.listComposable
import levilin.todocompose.navigation.destination.splashComposable
import levilin.todocompose.navigation.destination.taskComposable
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.viewmodel.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(navHostController: NavHostController, sharedViewModel: SharedViewModel) {

    val screen = remember(navHostController) {
        ScreenNavigation(navHostController = navHostController)
    }
    
    NavHost(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        navController = navHostController,
        startDestination = ConstantValue.SPLASH_SCREEN
    ) {
        splashComposable(navigateToListScreen = screen.splashScreen)
        listComposable(navigateToTaskScreen = screen.listScreen, sharedViewModel= sharedViewModel)
        taskComposable(navigateToListScreen = screen.taskScreen, sharedViewModel = sharedViewModel)
    }
}