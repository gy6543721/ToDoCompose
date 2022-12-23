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
import levilin.todocompose.utility.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListContent(allData: DataRequestState<List<ToDoTask>>, searchedData: DataRequestState<List<ToDoTask>>, searchAppBarState: SearchAppBarState, navigationToTaskScreen:(taskID: Int) -> Unit) {
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        if (searchedData is DataRequestState.Success) {
            HandleListContent(toDoTaskList = searchedData.data, navigationToTaskScreen = navigationToTaskScreen)
        }
    } else {
        if (allData is DataRequestState.Success) {
            HandleListContent(toDoTaskList = allData.data, navigationToTaskScreen = navigationToTaskScreen)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(toDoTaskList: List<ToDoTask>, navigationToTaskScreen:(taskID: Int) -> Unit) {
    if(toDoTaskList.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(toDoTaskList = toDoTaskList, navigationToTaskScreen = navigationToTaskScreen)
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(toDoTaskList: List<ToDoTask>, navigationToTaskScreen:(taskID: Int) -> Unit) {
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