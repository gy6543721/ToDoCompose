package levilin.todocompose.utility

enum class ActionValue {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

// Convert String to ActionValue
fun String?.toActionValue(): ActionValue {
    return when {
        this == "ADD" -> { ActionValue.ADD }
        this == "UPDATE" -> { ActionValue.UPDATE }
        this == "DELETE" -> { ActionValue.DELETE }
        this == "DELETE_ALL" -> { ActionValue.DELETE_ALL }
        this == "UNDO" -> { ActionValue.UNDO }
        else -> { ActionValue.NO_ACTION }
    }
}