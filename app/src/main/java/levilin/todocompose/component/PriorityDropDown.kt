package levilin.todocompose.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.data.model.Priority
import levilin.todocompose.ui.theme.*

@Composable
fun PriorityDropDown(priority: Priority, onPrioritySelected:(Priority) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )
    
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .height(PRIORITY_DROP_DOWN_HEIGHT)
        .clickable { expanded = true }
        .border(
            width = PRIORITY_DROP_DOWN_BOARDER,
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
            shape = MaterialTheme.shapes.small
        ), verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier
            .size(PRIORITY_INDICATOR_SIZE)
            .weight(1f)) {
            drawCircle(color = priority.color)
        }
        Text(modifier = Modifier.weight(8f), text = priority.name, style = MaterialTheme.typography.subtitle2)
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(weight = 1.5f),
            onClick = { expanded = true }) {
            Icon(imageVector = Icons.Filled.ArrowDropDown , contentDescription = stringResource(id = R.string.drop_down_icon_text))
        }
        DropdownMenu(modifier = Modifier.fillMaxWidth(fraction = 0.9f), expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.LOW)
                }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.MEDIUM)
                }) {
                PriorityItem(priority = Priority.MEDIUM)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.HIGH)
                }) {
                PriorityItem(priority = Priority.HIGH)
            }
        }
    }
}

@Composable
@Preview
fun PriorityDropDownPreview() {
    PriorityDropDown(priority = Priority.LOW, onPrioritySelected = {})
}