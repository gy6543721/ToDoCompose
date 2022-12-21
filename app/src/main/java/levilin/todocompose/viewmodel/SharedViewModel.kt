package levilin.todocompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.data.repository.ToDoRepository
import levilin.todocompose.utility.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val toDoRepository: ToDoRepository) : ViewModel() {

    // AppBar State
    var searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    var searchTextState: MutableState<String> = mutableStateOf("")

    // Task State
    private val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val allTasks:StateFlow<List<ToDoTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            toDoRepository.getAllTasks.collect() {
                _allTasks.value = it
            }
        }
    }
}