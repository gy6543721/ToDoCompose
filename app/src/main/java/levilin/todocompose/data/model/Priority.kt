package levilin.todocompose.data.model

import androidx.compose.ui.graphics.Color
import levilin.todocompose.ui.theme.*

enum class Priority(val color: Color) {
    HIGH(HighPriority),
    MEDIUM(MediumPriority),
    LOW(LowPriority),
    NO(NoPriority)
}