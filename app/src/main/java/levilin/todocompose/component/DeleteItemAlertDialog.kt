package levilin.todocompose.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import levilin.todocompose.R
import levilin.todocompose.ui.theme.topAppBarBackgroundColor
import levilin.todocompose.ui.theme.topAppBarItemColor

@Composable
fun DeleteItemAlertDialog(title: String, message: String, launchDialog: Boolean, closeDialog:() -> Unit, onConfirmClicked:() -> Unit) {
    if (launchDialog) {
        AlertDialog(
            title = { Text(text = title, fontSize = MaterialTheme.typography.h5.fontSize, fontWeight = FontWeight.Bold) },
            text = { Text(text = message, fontSize = MaterialTheme.typography.subtitle1.fontSize, fontWeight = FontWeight.Normal) },
            confirmButton = {
                Button( colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor), onClick = {
                    onConfirmClicked()
                    closeDialog()
                }) {
                    Text(color = MaterialTheme.colors.topAppBarItemColor, text = stringResource(id = R.string.alert_dialog_button_confirm))
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor), onClick = { closeDialog() }) {
                    Text(color = MaterialTheme.colors.topAppBarItemColor, text = stringResource(id = R.string.alert_dialog_button_cancel))
                }
            },
            onDismissRequest = { closeDialog() })
    }
}