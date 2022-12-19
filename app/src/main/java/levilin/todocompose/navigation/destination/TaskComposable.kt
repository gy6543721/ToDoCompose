package levilin.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.ConstantValue

fun NavGraphBuilder.taskComposable(navigateToListScreen: (ActionValue) -> Unit) {
    composable(route = ConstantValue.TASK_SCREEN, arguments = listOf(navArgument(ConstantValue.TASK_ARGUMENT_KEY){
        type = NavType.IntType
    })) {

    }
}