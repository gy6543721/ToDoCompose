package levilin.todocompose.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import levilin.todocompose.data.ToDoDAO
import levilin.todocompose.data.ToDoDatabase
import levilin.todocompose.data.model.ToDoTask
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDAO: ToDoDAO) {

    val getAllTasks: Flow<List<ToDoTask>> = toDoDAO.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDAO.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDAO.sortByHighPriority()

    fun getSelectedTask(taskID: Int):Flow<ToDoTask> {
        return toDoDAO.getSelectedTask(taskID)
    }

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDAO.addTask(toDoTask = toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDAO.updateTask(toDoTask = toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDAO.deleteTask(toDoTask = toDoTask)
    }

    suspend fun deleteAllTasks() {
        toDoDAO.deleteAllTasks()
    }

    fun searchDataBase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDAO.searchDataBase(searchQuery = searchQuery)
    }
}