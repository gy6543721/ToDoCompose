package levilin.todocompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.data.repository.ToDoRepository
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.utility.DataRequestState
import levilin.todocompose.utility.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val toDoRepository: ToDoRepository) : ViewModel() {

    // Task Content
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    // AppBar State
    var searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    var searchTextState: MutableState<String> = mutableStateOf("")

    // Task State
    private val _allTasks = MutableStateFlow<DataRequestState<List<ToDoTask>>>(DataRequestState.Idle)
    val allTasks:StateFlow<DataRequestState<List<ToDoTask>>> = _allTasks

    fun getAllTasks() {
        _allTasks.value = DataRequestState.Loading

        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks.collect() { listToDoTask ->
                    _allTasks.value = DataRequestState.Success(data =  listToDoTask)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = DataRequestState.Failed(error = e)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTasks(taskID: Int) {
        viewModelScope.launch {
            toDoRepository.getSelectedTask(taskID = taskID).collect(){ selectedToDoTask ->
                _selectedTask.value = selectedToDoTask
            }
        }
    }

    fun updateTaskContent(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            priority.value = selectedTask.priority
            description.value = selectedTask.description
        } else {
            id.value = 0
            title.value = ""
            priority.value = Priority.LOW
            description.value = ""
        }
    }

    fun updateTaskTitle(newTitle: String) {
        if (newTitle.length < ConstantValue.TASK_TITLE_LIMIT_LENGTH) {
            title.value = newTitle
        }
    }

    fun checkTaskContentNotEmpty(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }
}