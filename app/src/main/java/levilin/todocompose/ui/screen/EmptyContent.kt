package levilin.todocompose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import levilin.todocompose.R
import levilin.todocompose.ui.theme.*

@Composable
fun EmptyContent() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(modifier = Modifier.size(EMPTY_ICON_SIZE), painter = painterResource(id = R.drawable.ic_baseline_device_unknown), contentDescription = stringResource(id = R.string.empty_item_icon), tint = MediumGray)
        Text(text = stringResource(id = R.string.empty_content_text), color = MediumGray, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.h6.fontSize)
    }
}

@Composable
@Preview
private fun EmptyContentPreview() {
    EmptyContent()
}