package levilin.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import levilin.todocompose.ui.screen.ListScreen
import levilin.todocompose.utility.ConstantValue

fun NavGraphBuilder.listComposable(navigateToTaskScreen: (Int) -> Unit) {
    composable(route = ConstantValue.LIST_SCREEN, arguments = listOf(navArgument(ConstantValue.LIST_ARGUMENT_KEY){
        type = NavType.StringType
    })) {
        ListScreen(navigationToTaskScreen = navigateToTaskScreen)
    }
}