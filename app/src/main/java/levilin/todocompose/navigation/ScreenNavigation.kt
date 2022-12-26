package levilin.todocompose.navigation

import androidx.navigation.NavHostController
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.ConstantValue

class ScreenNavigation(navHostController: NavHostController) {

    val splashScreen:() -> Unit = {
        navHostController.navigate("list/${ActionValue.NO_ACTION.name}") {
            // remove this screen to disable back stack
            popUpTo(ConstantValue.SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }

    val listScreen:(Int) -> Unit = { taskID ->
        navHostController.navigate("task/${taskID}") {
        }
    }

    val taskScreen:(ActionValue) -> Unit = { actionValue ->
        navHostController.navigate("list/${actionValue.name}") {
            // remove this screen to disable back stack
            popUpTo(ConstantValue.LIST_SCREEN) {
                inclusive = true
            }
        }
    }
}