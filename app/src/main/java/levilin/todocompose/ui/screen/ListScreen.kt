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
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(navigationToTaskScreen: (taskID: Int) -> Unit, sharedViewModel: SharedViewModel) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
        sharedViewModel.getSortState()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val actionValue: ActionValue by sharedViewModel.actionValue
    val scaffoldState = rememberScaffoldState()

    DisplayActionMessage(
        scaffoldState = scaffoldState,
        handleDatabaseAction = { sharedViewModel.handleDatabaseAction(actionValue = actionValue) },
        taskTitle = sharedViewModel.title.value,
        actionValue = actionValue,
        onUndoClicked = { undoActionValue -> sharedViewModel.actionValue.value = undoActionValue }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        content = { ListContent(
            allData = allTasks,
            searchedData = searchedTasks,
            lowPriorityData = lowPriorityTasks,
            highPriorityData = highPriorityTasks,
            sortState = sortState,
            searchAppBarState = searchAppBarState,
            onSwipeToDelete = { actionValue, task ->
                sharedViewModel.actionValue.value = actionValue
                sharedViewModel.updateTaskContent(selectedTask = task)
            },
            navigationToTaskScreen = navigationToTaskScreen) },
        topBar = { ListAppBar(sharedViewModel= sharedViewModel, searchAppBarState= searchAppBarState, searchTextState = searchTextState) },
        floatingActionButton = { ListFloatingActionButton(navigationToTaskScreen = navigationToTaskScreen) }
    )
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
                    message = setDisplayMessage(actionValue = actionValue, itemTitle = taskTitle),
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

private fun setDisplayMessage(actionValue: ActionValue, itemTitle: String): String {
    return when (actionValue) {
        ActionValue.ADD -> "Add item $itemTitle"
        ActionValue.UPDATE -> "Update item $itemTitle"
        ActionValue.DELETE -> "Delete item $itemTitle"
        ActionValue.DELETE_ALL -> "All items are deleted"
        ActionValue.UNDO -> "Undo delete item $itemTitle"
        else -> "${actionValue.name} $itemTitle"
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