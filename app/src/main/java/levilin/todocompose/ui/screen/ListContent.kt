package levilin.todocompose.ui.screen

import android.util.Log
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.delay
import levilin.todocompose.R
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.ui.theme.*
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.utility.DataRequestState
import levilin.todocompose.utility.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListContent(
    allData: DataRequestState<List<ToDoTask>>,
    searchedData: DataRequestState<List<ToDoTask>>,
    lowPriorityData: List<ToDoTask>,
    highPriorityData: List<ToDoTask>,
    sortState: DataRequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete:(ActionValue, ToDoTask) -> Unit,
    navigationToTaskScreen:(taskID: Int) -> Unit
) {
    if (sortState is DataRequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedData is DataRequestState.Success) {
                    Log.d("TAG", "ListContent SearchedData:" + searchedData.data.toString())
                    HandleListContent(toDoTaskList = searchedData.data, onSwipeToDelete = onSwipeToDelete, navigationToTaskScreen = navigationToTaskScreen)
                }
            }
            sortState.data == Priority.NO -> {
                if (allData is DataRequestState.Success) {
                    HandleListContent(toDoTaskList = allData.data, onSwipeToDelete = onSwipeToDelete, navigationToTaskScreen = navigationToTaskScreen)
                }
            }
            sortState.data == Priority.LOW -> {
                if (allData is DataRequestState.Success) {
                    HandleListContent(toDoTaskList = lowPriorityData, onSwipeToDelete = onSwipeToDelete, navigationToTaskScreen = navigationToTaskScreen)
                }
            }
            sortState.data == Priority.HIGH -> {
                if (allData is DataRequestState.Success) {
                    HandleListContent(toDoTaskList = highPriorityData, onSwipeToDelete = onSwipeToDelete, navigationToTaskScreen = navigationToTaskScreen)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(toDoTaskList: List<ToDoTask>, onSwipeToDelete:(ActionValue, ToDoTask) -> Unit, navigationToTaskScreen:(taskID: Int) -> Unit) {
    if(toDoTaskList.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(toDoTaskList = toDoTaskList, onSwipeToDelete = onSwipeToDelete, navigationToTaskScreen = navigationToTaskScreen)
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(toDoTaskList: List<ToDoTask>, onSwipeToDelete:(ActionValue, ToDoTask) -> Unit, navigationToTaskScreen:(taskID: Int) -> Unit) {
    LazyColumn {
        items(items = toDoTaskList, key = { toDoTaskList -> toDoTaskList.id }) { toDoTask ->
            // Swipe to delete item
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            val degree by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) {
                    ConstantValue.SWIPE_DELETE_START_ANGLE
                } else {
                    ConstantValue.SWIPE_DELETE_END_ANGLE
                }
            )
            var itemAppeared: Boolean by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) { itemAppeared = true }

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val lifecycleScope = rememberCoroutineScope()
                LaunchedEffect(lifecycleScope) {
                    delay(ConstantValue.ANIMATION_VISIBILITY_DURATION.toLong())
                    onSwipeToDelete(ActionValue.DELETE, toDoTask)
                }

                // Use LaunchedEffect instead of launch
//                lifecycleScope.launch {
//                    delay(ConstantValue.ANIMATION_VISIBILITY_DURATION.toLong())
//                    onSwipeToDelete(ActionValue.DELETE, toDoTask)
//                }
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(animationSpec = tween(durationMillis = ConstantValue.ANIMATION_VISIBILITY_DURATION)),
                exit = shrinkVertically(animationSpec = tween(durationMillis = ConstantValue.ANIMATION_VISIBILITY_DURATION))
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(ConstantValue.SWIPE_DELETE_FRACTION) },
                    background = { SwipeDeleteBackground(rotateDegree = degree) },
                    dismissContent = { TaskItem(toDoTask = toDoTask, navigationToTaskScreen = navigationToTaskScreen) }
                )
            }
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

@Composable
fun SwipeDeleteBackground(rotateDegree: Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(HighPriority)
        .padding(SWIPE_DELETE_BACKGROUND_PADDING), contentAlignment = Alignment.CenterEnd) {
        Icon(modifier = Modifier.rotate(degrees = rotateDegree), imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.swipe_delete_background_icon), tint = Color.White)
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun ListContentPreview() {
    TaskItem(toDoTask = ToDoTask(0,"aaa","bbb",Priority.LOW), navigationToTaskScreen = {})
}