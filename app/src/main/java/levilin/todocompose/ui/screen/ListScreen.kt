package levilin.todocompose.ui.screen

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R

@Composable
fun ListScreen(navigationToTaskScreen: (Int) -> Unit) {
    Scaffold(
        content = {},
        floatingActionButton = {
            ListFloatingActionButton(navigationToTaskScreen = navigationToTaskScreen)
        })
}

@Composable
fun ListFloatingActionButton(navigationToTaskScreen: (Int) -> Unit) {
    FloatingActionButton(onClick = {navigationToTaskScreen(-1)}) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.floating_add_button),
            tint = Color.White,
        )
    }
}

@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(navigationToTaskScreen = {})
}