package levilin.todocompose.navigation

import androidx.navigation.NavHostController
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.ConstantValue.LIST_SCREEN

class ScreenNavigation(navHostController: NavHostController) {
    val list:(ActionValue) -> Unit = { actionValue ->
        navHostController.navigate("list/${actionValue.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val task:(Int) -> Unit = { taskID ->
        navHostController.navigate("task/${taskID}") {
        }
    }
}