package com.example.newsapp.ui.theme.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.AppViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsTable
import com.example.newsapp.ui.theme.NewsTopAppBar

import com.example.newsapp.ui.theme.navigation.NewsAppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    newsItem: NewsTable,
    navigateUp: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(topBar = {
        NewsTopAppBar(
            title = NewsAppScreens.DETAIL.name,
            canNavigateBack = true,
            scrollBehavior = scrollBehavior
        ) {
            navigateUp()
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsItem.urlToImage)
                    .build(),
                error = painterResource(id = R.drawable.netowrk_error),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp),
                contentScale = ContentScale.Crop
            )
            TextDescription(
                newState = newsItem,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(top = 230.dp)
            )
        }
    }
}

@Composable
fun TextDescription(modifier: Modifier = Modifier, newState: NewsTable) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            text = newState.title.toString(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = newState.description.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = newState.content.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(
        newsItem = NewsTable(
            id = 0,
            description = "TEst Description",
            author = "",
            content = "",
            publishedAt = "",
            title = "TestTitle",
            url = "",
            urlToImage = ""
        )
    ) {}
}
