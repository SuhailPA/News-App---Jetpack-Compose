package com.example.newsapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    onUserClickBack: () -> Unit
) {
    BackHandler {
        onUserClickBack()
    }
    var textValue by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier.fillMaxSize()) {
        TextInputEditText(
            onValueChange = {
                textValue = it
            }, textValue = textValue,
            hint = "Title"
        )
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