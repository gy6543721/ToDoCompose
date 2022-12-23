package levilin.todocompose.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.component.PriorityItem
import levilin.todocompose.data.model.Priority
import levilin.todocompose.ui.theme.SMALL_PADDING
import levilin.todocompose.ui.theme.TOP_APPBAR_HEIGHT
import levilin.todocompose.ui.theme.topAppBarBackgroundColor
import levilin.todocompose.ui.theme.topAppBarItemColor
import levilin.todocompose.utility.SearchAppBarState
import levilin.todocompose.utility.SearchAppBarTrailingIconState
import levilin.todocompose.viewmodel.SharedViewModel
import androidx.compose.material.TextField as TextField

@Composable
fun ListAppBar(sharedViewModel: SharedViewModel, searchAppBarState: SearchAppBarState, searchTextState: String) {
    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                onDeleteAllClicked = {}
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChanged = { inputText ->  sharedViewModel.searchTextState.value = inputText },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = { inputText -> sharedViewModel.searchSelectedTasks(searchQuery = inputText)
                }
            )
        }
    }
}

@Composable
fun DefaultListAppBar(onSearchClicked:() -> Unit, onSortClicked:(Priority) -> Unit, onDeleteAllClicked:() -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.list_appbar_text_default), color = MaterialTheme.colors.topAppBarItemColor) },
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
    IconButton(onClick = { onSearchClicked() }) {
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
fun SearchAppBar(text: String, onTextChanged: (String) -> Unit, onCloseClicked: () -> Unit, onSearchClicked: (String) -> Unit) {
    var trailingIconState by remember {
        mutableStateOf(SearchAppBarTrailingIconState.READY_TO_CLOSE)
    }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(TOP_APPBAR_HEIGHT), color = MaterialTheme.colors.topAppBarBackgroundColor, elevation = AppBarDefaults.TopAppBarElevation) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { inputText -> onTextChanged(inputText) },
            placeholder = { Text(modifier = Modifier.alpha(ContentAlpha.medium), text = stringResource(id = R.string.search_appbar_placeholder_text_default), color = MaterialTheme.colors.topAppBarItemColor) },
            textStyle = TextStyle(color = MaterialTheme.colors.topAppBarItemColor, fontSize = MaterialTheme.typography.subtitle1.fontSize),
            singleLine = true,
            leadingIcon = { Icon(modifier = Modifier.alpha(ContentAlpha.disabled), imageVector = Icons.Filled.Search, contentDescription = stringResource(id = R.string.list_appbar_search_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)},
            trailingIcon = { IconButton(
                onClick = {
                    when(trailingIconState) {
                        SearchAppBarTrailingIconState.READY_TO_DELETE -> {
                            onTextChanged("")
                            trailingIconState = SearchAppBarTrailingIconState.READY_TO_CLOSE
                        }
                        SearchAppBarTrailingIconState.READY_TO_CLOSE -> {
                            if(text.isNotEmpty()) {
                                onTextChanged("")
                            } else {
                                onCloseClicked()
                                trailingIconState = SearchAppBarTrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                Icon(modifier = Modifier.alpha(ContentAlpha.medium), imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.search_appbar_trail_icon_text), tint = MaterialTheme.colors.topAppBarItemColor)}
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }) {  },
            colors = TextFieldDefaults.textFieldColors(cursorColor = MaterialTheme.colors.topAppBarItemColor, focusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, backgroundColor = Color.Transparent)
        )
    }
}


@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteAllClicked = {})
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(text = "", onTextChanged = {}, onCloseClicked = {}, onSearchClicked = {})
}


