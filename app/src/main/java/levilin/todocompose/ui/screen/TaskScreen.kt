package levilin.todocompose.ui.screen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.utility.ActionValue

@Composable
fun TaskScreen(selectedTask: ToDoTask?, navigationToListScreen:(ActionValue) -> Unit) {
    
    Scaffold(
        topBar = { TaskAppBar(selectedItem = selectedTask, navigationToListScreen = navigationToListScreen) },
        content = {
            TaskContent(
                title = "",
                onTitleChanged = {},
                priority = Priority.LOW,
                onPriorityChanged = {},
                description = "",
                onDescriptionChanged = {}
            )
        })
}