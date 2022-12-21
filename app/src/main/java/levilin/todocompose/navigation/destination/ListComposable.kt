package levilin.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import levilin.todocompose.ui.screen.ListScreen
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.viewmodel.SharedViewModel

fun NavGraphBuilder.listComposable(navigateToTaskScreen: (taskID: Int) -> Unit, sharedViewModel: SharedViewModel) {
    composable(route = ConstantValue.LIST_SCREEN, arguments = listOf(navArgument(ConstantValue.LIST_ARGUMENT_KEY){
        type = NavType.StringType
    })) {
        ListScreen(navigationToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)
    }
}