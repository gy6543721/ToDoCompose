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
import levilin.todocompose.utility.DataRequestState
import levilin.todocompose.utility.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val toDoRepository: ToDoRepository) : ViewModel() {

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
                toDoRepository.getAllTasks.collect() {
                    _allTasks.value = DataRequestState.Success(data = it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = DataRequestState.Failed(error = e)
        }
    }
}