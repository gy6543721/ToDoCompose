package levilin.todocompose.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.ui.theme.*
import levilin.todocompose.utility.DataRequestState

@ExperimentalMaterialApi
@Composable
fun ListContent(rawData: DataRequestState<List<ToDoTask>>, navigationToTaskScreen:(taskID: Int) -> Unit) {
    if(rawData is DataRequestState.Success) {
        if(rawData.data.isEmpty()) {
            EmptyContent()
        } else {
            DisplayAllTasks(toDoTaskList = rawData.data, navigationToTaskScreen = navigationToTaskScreen)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayAllTasks(toDoTaskList: List<ToDoTask>, navigationToTaskScreen:(taskID: Int) -> Unit) {
    LazyColumn {
        items(items = toDoTaskList, key = { toDoTaskList -> toDoTaskList.id }) { toDoTaskList ->
            TaskItem(toDoTask = toDoTaskList, navigationToTaskScreen = navigationToTaskScreen)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(toDoTask: ToDoTask, navigationToTaskScreen:(taskID: Int) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.taskItemBackgroundColor, shape = RectangleShape, elevation = TASK_ITEM_ELEVATION, onClick = {
        navigationToTaskScreen(toDoTask.id)
    }) {
        Column(modifier = Modifier
            .padding(all = LARGE_PADDING)
            .fillMaxWidth()) {
            Row {
                Text(modifier = Modifier.weight(8f), text = toDoTask.title, color = MaterialTheme.colors.taskItemTextColor, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold, maxLines = 1)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.TopEnd) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(toDoTask.priority.color)
                    }
                }
            }
            Text(text = toDoTask.description, color = MaterialTheme.colors.taskItemTextColor, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun ListContentPreview() {
    TaskItem(toDoTask = ToDoTask(0,"aaa","bbb",Priority.LOW), navigationToTaskScreen = {})
}