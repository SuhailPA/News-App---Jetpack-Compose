package com.example.newsapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.NotificationData
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    onUserClickBack: () -> Unit,
    onTitleValueUpdated: (String) -> Unit,
    onDescriptionValueUpdated: (String) -> Unit,
    onButtonClick : () -> Unit,
    notificationState: NotificationData
) {
    BackHandler {
        onUserClickBack()
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        TextInputEditText(
            onValueChange = {
                onTitleValueUpdated(it)
            }, textValue = notificationState.title,
            hint = "Title"
        )

        TextInputEditText(
            onValueChange = {
                onDescriptionValueUpdated(it)
            }, textValue = notificationState.body,
            hint = "Description"
        )

        TextButton(onClick = { onButtonClick() }) {
            Text(text = "Sent Notification")
        }
    }


}

@Composable
fun TextInputEditText(
    onValueChange: (String) -> Unit,
    textValue: String,
    hint: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        value = textValue,
        onValueChange = onValueChange,
        label = { Text(text = hint) },
        shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNotificationScreen() {
    NewsAppTheme {
//        NotificationScreen()
    }
}