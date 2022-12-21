package levilin.todocompose.utility

sealed class DataRequestState<out T> {
    object Idle: DataRequestState<Nothing>()
    object Loading: DataRequestState<Nothing>()
    data class Success<T>(val data: T): DataRequestState<T>()
    data class Failed(val error: Throwable): DataRequestState<Nothing>()
}
