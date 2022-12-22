package levilin.todocompose.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import levilin.todocompose.R
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.viewmodel.SharedViewModel

@Composable
fun TaskScreen(selectedTask: ToDoTask?, sharedViewModel: SharedViewModel, navigationToListScreen:(ActionValue) -> Unit) {

    val title: String by sharedViewModel.title
    val priority: Priority by sharedViewModel.priority
    val description: String by sharedViewModel.description

    val context = LocalContext.current
    
    Scaffold(
        topBar = { TaskAppBar(selectedItem = selectedTask, navigationToListScreen = { action ->
            if (action == ActionValue.NO_ACTION) {
                navigationToListScreen(action)
            } else {
                if (sharedViewModel.checkTaskContentNotEmpty()) {
                    navigationToListScreen(action)
                } else {
                    displayToast(context = context)
                }
            }
        }) },
        content = {
            TaskContent(
                title = title,
                onTitleChanged = { inputTitleText ->
                    sharedViewModel.updateTaskTitle(inputTitleText)
                },
                priority = priority,
                onPriorityChanged = { selectedPriority ->
                    sharedViewModel.priority.value = selectedPriority
                },
                description = description,
                onDescriptionChanged = { inputDescriptionText ->
                    sharedViewModel.description.value = inputDescriptionText
                }
            )
        })
}

fun displayToast(context: Context) {
    Toast.makeText(context, R.string.toast_fields_empty_text, Toast.LENGTH_SHORT).show()
}