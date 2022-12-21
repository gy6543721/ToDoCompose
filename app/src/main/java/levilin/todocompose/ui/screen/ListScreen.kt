package levilin.todocompose.ui.screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.ui.theme.floatingAddButtonColor
import levilin.todocompose.utility.SearchAppBarState
import levilin.todocompose.viewmodel.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun ListScreen(navigationToTaskScreen: (taskID: Int) -> Unit, sharedViewModel: SharedViewModel) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    Scaffold(
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

//@ExperimentalMaterialApi
//@Composable
//@Preview
//private fun ListScreenPreview() {
//    ListScreen(navigationToTaskScreen = {}, sharedViewModel = {})
//}