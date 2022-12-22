package levilin.todocompose.navigation.destination

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import levilin.todocompose.ui.screen.ListScreen
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.utility.toActionValue
import levilin.todocompose.viewmodel.SharedViewModel

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(navigateToTaskScreen: (taskID: Int) -> Unit, sharedViewModel: SharedViewModel) {
    composable(route = ConstantValue.LIST_SCREEN, arguments = listOf(navArgument(ConstantValue.LIST_ARGUMENT_KEY){
        type = NavType.StringType
    })) { navBackStackEntry ->
        val actionValue = navBackStackEntry.arguments?.getString(ConstantValue.LIST_ARGUMENT_KEY).toActionValue()

        LaunchedEffect(key1 = actionValue) {
            sharedViewModel.actionValue.value = actionValue
        }

        ListScreen(navigationToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)
    }
}