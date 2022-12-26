package levilin.todocompose.utility

object ConstantValue {
    // Database
    const val DATABASE_TABLE = "todo_table"
    const val DATABASE_NAME = "todo_database"

    // DataStore
    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_KEY = "sort_state"

    // Screen
    const val SPLASH_SCREEN = "splashScreen"
    const val LIST_SCREEN = "list/{actionValue}"
    const val TASK_SCREEN = "task/{taskID}"

    // Composable
    const val LIST_ARGUMENT_KEY = "actionValue"
    const val TASK_ARGUMENT_KEY = "taskID"

    // Task Content
    const val TASK_TITLE_LIMIT_LENGTH = 25

    // Swipe Delete
    const val SWIPE_DELETE_FRACTION = 0.25f
    const val SWIPE_DELETE_START_ANGLE = 0f
    const val SWIPE_DELETE_END_ANGLE = -45f
    const val ANIMATION_VISIBILITY_DURATION = 300

    // Splash Screen Animation
    const val SPLASH_SCREEN_DELAY = 1500
    const val SPLASH_SCREEN_ANIMATION_DURATION = 1000
    const val SPLASH_SCREEN_ANIMATION_ALPHA_START = 0f
    const val SPLASH_SCREEN_ANIMATION_ALPHA_END = 1f
}