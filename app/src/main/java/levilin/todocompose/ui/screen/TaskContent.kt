package levilin.todocompose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.component.PriorityDropDown
import levilin.todocompose.data.model.Priority
import levilin.todocompose.ui.theme.*

@Composable
fun TaskContent(title: String, onTitleChanged:(String) -> Unit, priority: Priority, onPriorityChanged:(Priority) -> Unit, description: String, onDescriptionChanged:(String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
        .padding(all = LARGE_PADDING)) {

        // Title
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title, onValueChange = { onTitleChanged(it) },
            label = { Text(text = stringResource(id = R.string.task_content_title_text)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MaterialTheme.colors.primary
            )
        )

        // Divider padding
        Divider(modifier = Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colors.background)

        // Priority drop down menu
        PriorityDropDown(priority = priority, onPrioritySelected = onPriorityChanged)

        // Description
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description, onValueChange = { onDescriptionChanged(it) },
            label = { Text(text = stringResource(id = R.string.task_content_description_text)) },
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MaterialTheme.colors.primary
            )
        )
    }
}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        title = "",
        onTitleChanged = {},
        priority = Priority.LOW,
        onPriorityChanged = {},
        description = "",
        onDescriptionChanged = {}
    )
}