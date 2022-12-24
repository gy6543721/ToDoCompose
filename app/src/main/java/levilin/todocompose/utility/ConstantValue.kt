package levilin.todocompose.utility

object ConstantValue {
    // Database
    const val DATABASE_TABLE = "todo_table"
    const val DATABASE_NAME = "todo_database"

    // DataStore
    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_KEY = "sort_state"

    // Screen
    const val LIST_SCREEN = "list/{actionValue}"
    const val TASK_SCREEN = "task/{taskID}"

    // Composable
    const val LIST_ARGUMENT_KEY = "actionValue"
    const val TASK_ARGUMENT_KEY = "taskID"

    // Task Content
    const val TASK_TITLE_LIMIT_LENGTH = 25
}