package levilin.todocompose.ui.screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import levilin.todocompose.R
import levilin.todocompose.ui.theme.floatingAddButtonColor
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.SearchAppBarState
import levilin.todocompose.viewmodel.SharedViewModel
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(navigationToTaskScreen: (taskID: Int) -> Unit, sharedViewModel: SharedViewModel) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val actionValue: ActionValue by sharedViewModel.actionValue
    val scaffoldState = rememberScaffoldState()

    DisplayActionMessage(
        scaffoldState = scaffoldState,
        handleDatabaseAction = { sharedViewModel.handleDatabaseAction(actionValue = actionValue) },
        taskTitle = sharedViewModel.title.value,
        actionValue = actionValue,
        onUndoClicked = { actionValue ->  
            sharedViewModel.actionValue.value = actionValue
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        content = { ListContent(rawData = allTasks, navigationToTaskScreen = navigationToTaskScreen) },
        topBar = {
            ListAppBar(sharedViewModel= sharedViewModel, searchAppBarState= searchAppBarState, searchTextState = searchTextState)
        },
        floatingActionButton = {
            ListFloatingActionButton(navigationToTaskScreen = navigationToTaskScreen)
        })
}

@Composable
fun ListFloatingActionButton(navigationToTaskScreen: (taskID: Int) -> Unit) {
    FloatingActionButton(onClick = {navigationToTaskScreen(-1)}, backgroundColor = MaterialTheme.colors.floatingAddButtonColor) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.floating_add_button),
            tint = Color.White,
        )
    }
}

@Composable
fun DisplayActionMessage(scaffoldState: ScaffoldState, handleDatabaseAction:() -> Unit, taskTitle: String, actionValue: ActionValue, onUndoClicked:(ActionValue) -> Unit) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = actionValue) {
        if (actionValue != ActionValue.NO_ACTION) {
            scope.launch {
                val actionMessage = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${actionValue.name} $taskTitle",
                    actionLabel = setActionLabel(actionValue = actionValue),
                    duration = SnackbarDuration.Short
                )
                undoDeleteTask(actionValue = actionValue, snackBarResult = actionMessage, onUndoClicked = onUndoClicked)
            }
        }
    }
}

private fun setActionLabel(actionValue: ActionValue): String {
    return if (actionValue == ActionValue.DELETE) {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeleteTask(actionValue: ActionValue, snackBarResult: SnackbarResult, onUndoClicked:(ActionValue) -> Unit) {
    if (snackBarResult == SnackbarResult.ActionPerformed && actionValue == ActionValue.DELETE) {
        onUndoClicked(ActionValue.UNDO)
    }
}


//@ExperimentalMaterialApi
//@Composable
//@Preview
//private fun ListScreenPreview() {
//    ListScreen(navigationToTaskScreen = {}, sharedViewModel = {})
//}