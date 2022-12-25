package levilin.todocompose.navigation.destination

import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import levilin.todocompose.ui.screen.TaskScreen
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.viewmodel.SharedViewModel

fun NavGraphBuilder.taskComposable(navigateToListScreen: (ActionValue) -> Unit, sharedViewModel: SharedViewModel) {
    composable(route = ConstantValue.TASK_SCREEN, arguments = listOf(navArgument(ConstantValue.TASK_ARGUMENT_KEY){
        type = NavType.IntType
    })) { navBackStackEntry ->
        val taskID = navBackStackEntry.arguments!!.getInt(ConstantValue.TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskID) {
            sharedViewModel.getSelectedTasks(taskID = taskID)
        }

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskID == -1) {
                sharedViewModel.updateTaskContent(selectedTask = selectedTask)
            }
        }

        TaskScreen(selectedTask = selectedTask, sharedViewModel = sharedViewModel, navigationToListScreen = navigateToListScreen)
    }
}