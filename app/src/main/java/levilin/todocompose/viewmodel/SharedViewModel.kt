package levilin.todocompose.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import levilin.todocompose.data.model.Priority
import levilin.todocompose.data.model.ToDoTask
import levilin.todocompose.data.repository.DataStoreRepository
import levilin.todocompose.data.repository.ToDoRepository
import levilin.todocompose.utility.ActionValue
import levilin.todocompose.utility.ConstantValue
import levilin.todocompose.utility.DataRequestState
import levilin.todocompose.utility.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val toDoRepository: ToDoRepository, private val dataStoreRepository: DataStoreRepository) : ViewModel() {

    // ActionValue
    val actionValue: MutableState<ActionValue> = mutableStateOf(ActionValue.NO_ACTION)

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

    // Search Task
    private val _searchedTasks = MutableStateFlow<DataRequestState<List<ToDoTask>>>(DataRequestState.Idle)
    val searchedTasks:StateFlow<DataRequestState<List<ToDoTask>>> = _searchedTasks

    // Get Selected Task
    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    // Sort Task
    private val _sortState = MutableStateFlow<DataRequestState<Priority>>(DataRequestState.Idle)
    val sortState: StateFlow<DataRequestState<Priority>> = _sortState
    val lowPriorityTasks: StateFlow<List<ToDoTask>> = toDoRepository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )
    val highPriorityTasks: StateFlow<List<ToDoTask>> = toDoRepository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    init {
        getAllTasks()
        getSortState()
    }

    private fun getAllTasks() {
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

    fun getSelectedTasks(taskID: Int) {
        viewModelScope.launch {
            toDoRepository.getSelectedTask(taskID = taskID).collect { selectedToDoTask ->
                _selectedTask.value = selectedToDoTask
                Log.d("TAG", ("SVM _selectedTask: " + _selectedTask.value!!.title))
            }
        }
    }

    private fun getSortState() {
        _sortState.value = DataRequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readDataStore.map { priority ->
                    Priority.valueOf(priority)
                }.collect(){ priority ->
                    _sortState.value = DataRequestState.Success(priority)
                }
            }
        } catch (e: Exception) {
            _sortState.value = DataRequestState.Failed(error = e)
        }
    }

    fun searchSelectedTasks(searchQuery: String) {
        _searchedTasks.value = DataRequestState.Loading

        try {
            viewModelScope.launch {
                toDoRepository.searchDataBase(searchQuery = "%$searchQuery%").collect { searchResult ->
                    _searchedTasks.value = DataRequestState.Success(searchResult)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = DataRequestState.Failed(error = e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
        Log.d("TAG", ("SVM _searchedTask: " + _searchedTasks.value))
        Log.d("TAG", ("SVM searchedTask: " + searchedTasks.value))
    }

    fun handleDatabaseAction(actionValue: ActionValue) {
        when(actionValue) {
            ActionValue.ADD -> { addTask() }
            ActionValue.UPDATE -> { updateTask() }
            ActionValue.DELETE -> { deleteTask() }
            ActionValue.DELETE_ALL -> { deleteAllTask() }
            ActionValue.UNDO -> { addTask() }
            else -> {}
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(title = title.value, description = description.value, priority = priority.value)
            toDoRepository.addTask(toDoTask = toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(id = id.value, title = title.value, description = description.value, priority = priority.value)
            toDoRepository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(id = id.value, title = title.value, description = description.value, priority = priority.value)
            toDoRepository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllTasks()
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

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }
}