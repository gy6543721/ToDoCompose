package levilin.todocompose.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.component.PriorityItem
import levilin.todocompose.data.model.Priority
import levilin.todocompose.ui.theme.SMALL_PADDING
import levilin.todocompose.ui.theme.topAppBarBackgroundColor
import levilin.todocompose.ui.theme.topAppBarItemColor

@Composable
fun ListAppBar() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteAllClicked = {})
}

@Composable
fun DefaultListAppBar(onSearchClicked:() -> Unit, onSortClicked:(Priority) -> Unit, onDeleteAllClicked:() -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.default_list_appbar_text), color = MaterialTheme.colors.topAppBarItemColor) },
        actions = { ListAppBarAction(onSearchClicked = onSearchClicked, onSortClicked = onSortClicked, onDeleteAllClicked = onDeleteAllClicked) },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor)
}

@Composable
fun ListAppBarAction(onSearchClicked:() -> Unit, onSortClicked:(Priority) -> Unit, onDeleteAllClicked: () -> Unit) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction (onDeleteAllClicked = onDeleteAllClicked)
}

// Define each action
@Composable
fun SearchAction(onSearchClicked:() -> Unit) {
    IconButton(onClick = {onSearchClicked()}) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.list_appbar_search_icon_text),
            tint = MaterialTheme.colors.topAppBarItemColor
        )
    }
}

@Composable
fun SortAction(onSortClicked:(Priority) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    
    IconButton(onClick = { expanded = true }) {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_filter_list), contentDescription = stringResource(id = R.string.list_appbar_sort_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(Priority.LOW)
            }) { PriorityItem(priority = Priority.LOW) }

            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(Priority.HIGH)
            }) { PriorityItem(priority = Priority.HIGH) }

            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(Priority.NONE)
            }) { PriorityItem(priority = Priority.NONE) }
        }
    }
}

@Composable
fun DeleteAllAction(onDeleteAllClicked:() -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert),
            contentDescription = stringResource(id = R.string.list_appbar_delete_all_icon_text),
            tint = MaterialTheme.colors.topAppBarItemColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteAllClicked()
            }) {
                Text(
                    modifier = Modifier.padding(SMALL_PADDING),
                    color = MaterialTheme.colors.onSurface,
                    text = stringResource(id = R.string.list_appbar_delete_all_icon_text)
                )
            }
        }
    }
}

@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteAllClicked = {})
}


