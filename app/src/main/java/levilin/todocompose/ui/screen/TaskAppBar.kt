package levilin.todocompose.ui.screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.ui.theme.topAppBarBackgroundColor
import levilin.todocompose.ui.theme.topAppBarItemColor
import levilin.todocompose.utility.ActionValue

@Composable
fun TaskAppBar(selectedItem: ToDoTask?, navigationToListScreen:(ActionValue) -> Unit) {
    if(selectedItem == null) {
        NewTaskAppBar(navigationToListScreen = navigationToListScreen)
    } else {
        ExistingTaskAppBar(selectedItem = selectedItem, navigationToListScreen = navigationToListScreen)
    }
}

@Composable
fun NewTaskAppBar(navigationToListScreen:(ActionValue) -> Unit) {
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigationToListScreen) },
        title = { Text(text = stringResource(id = R.string.task_appbar_text_default), color = MaterialTheme.colors.topAppBarItemColor) },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = { AddAction(onAddClicked = navigationToListScreen) }
    )
}

@Composable
fun ExistingTaskAppBar(selectedItem: ToDoTask, navigationToListScreen:(ActionValue) -> Unit) {
    TopAppBar(
        navigationIcon = { CloseAction(onCloseClicked = navigationToListScreen) },
        title = { Text(text = selectedItem.title, color = MaterialTheme.colors.topAppBarItemColor, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = { 
            DeleteAction(onDeleteClicked = navigationToListScreen)
            UpdateAction(onUpdateClicked = navigationToListScreen)
        }
    )
}

@Composable
fun BackAction(onBackClicked:(ActionValue) -> Unit) {
    IconButton(onClick = { onBackClicked(ActionValue.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.task_appbar_back_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)
    }
}

@Composable
fun AddAction(onAddClicked:(ActionValue) -> Unit) {
    IconButton(onClick = { onAddClicked(ActionValue.ADD) }) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = stringResource(id = R.string.task_appbar_add_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)
    }
}

@Composable
fun CloseAction(onCloseClicked:(ActionValue) -> Unit) {
    IconButton(onClick = { onCloseClicked(ActionValue.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.task_appbar_close_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)
    }
}

@Composable
fun DeleteAction(onDeleteClicked:(ActionValue) -> Unit) {
    IconButton(onClick = { onDeleteClicked(ActionValue.DELETE) }) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.task_appbar_delete_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)
    }
}

@Composable
fun UpdateAction(onUpdateClicked:(ActionValue) -> Unit) {
    IconButton(onClick = { onUpdateClicked(ActionValue.UPDATE) }) {
        Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = stringResource(id = R.string.task_appbar_update_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)
    }
}

@Composable
@Preview
fun TaskAppBarPreview() {
    TaskAppBar(selectedItem = ToDoTask(id = 0, title = "aaa", description = "DDD", priority = Priority.LOW), navigationToListScreen = {})
}

@Composable
@Preview
fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(selectedItem = ToDoTask(id = 0, title = "aaa", description = "DDD", priority = Priority.LOW), navigationToListScreen = {})
}