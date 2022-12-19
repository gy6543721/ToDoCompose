package levilin.todocompose.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import levilin.todocompose.navigation.destination.listComposable
import levilin.todocompose.navigation.destination.taskComposable
import levilin.todocompose.utility.ConstantValue

@Composable
fun SetupNavigation(navHostController: NavHostController) {

    val screen = remember(navHostController) {
        ScreenNavigation(navHostController = navHostController)
    }
    
    NavHost(navController = navHostController, startDestination = ConstantValue.LIST_SCREEN) {
        listComposable(navigateToTaskScreen = screen.task)
        taskComposable(navigateToListScreen = screen.list)
    }
}