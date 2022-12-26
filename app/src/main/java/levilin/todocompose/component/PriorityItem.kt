package levilin.todocompose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.data.model.Priority
import levilin.todocompose.ui.theme.*
import java.util.*

@Composable
fun PriorityItem(priority: Priority) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        Text(modifier = Modifier.padding(start = SMALL_PADDING), text = priority.name, color = MaterialTheme.colors.onSurface)
        
    }
}

@Composable
fun PrioritySortItem(priority: Priority) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        Text(modifier = Modifier.padding(start = SMALL_PADDING), text = "Sort by ${priority.name.lowercase(Locale.getDefault())} priority", color = MaterialTheme.colors.onSurface)

    }
}

@Composable
@Preview
fun PriorityItemPreview() {
    PriorityItem(priority = Priority.LOW)
}

@Composable
@Preview
fun PrioritySortItemPreview() {
    PrioritySortItem(priority = Priority.LOW)
}