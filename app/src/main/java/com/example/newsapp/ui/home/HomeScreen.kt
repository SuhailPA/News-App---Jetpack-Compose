package com.example.newsapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsTable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    newsData: List<NewsTable>,
    onItemClick: (NewsTable) -> Unit
) {
    if (newsData.isNotEmpty()) {
        NewsList(
            news = newsData,
            modifier = Modifier.padding(),
            onItemClick = onItemClick
        )
    } else {
        LoadingErrorScreen(modifier = Modifier.padding())
    }

//    }
}

@Composable
fun NewsItem(modifier: Modifier = Modifier, news: NewsTable, onItemClick: (NewsTable) -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(news) },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextComposables(news = news, modifier = Modifier.weight(2f))
            AsyncImage(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(100.dp),
                model = ImageRequest.Builder(context)
                    .crossfade(true)
                    .data(news.urlToImage)
                    .build(),
                placeholder = painterResource(id = R.drawable.breaking_news_placeholder),
                error = painterResource(id = R.drawable.breaking_news_placeholder),
                contentDescription = news.title,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}


@Composable
fun TextComposables(modifier: Modifier = Modifier, news: NewsTable) {
    Column(modifier = modifier) {
        AuthorDetails(authorName = news.author)
        Text(
            text = news.title ?: "Sample",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(10.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = news.publishedAt ?: "",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun AuthorDetails(authorName: String?) {
    authorName?.let {
        Card(
            modifier = Modifier.padding(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(
                text = authorName,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    news: List<NewsTable>,
    onItemClick: (NewsTable) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            HorizontalAutoSlider(news = news, onItemClick = onItemClick)
        }
        items(items = news, key = { news -> news.title!! }) {
            NewsItem(news = it, onItemClick = onItemClick)
        }
    }
}

@Composable
fun LoadingErrorScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = null,
        contentScale = ContentScale.FillHeight
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewNewsItem() {
    NewsItem(
        news = NewsTable(
            author = "Suhail",
            content = "Test Content",
            description = "Test Description",
            publishedAt = "Monday",
            title = "Test Title",
            url = "Test URl",
            urlToImage = "Test Image"
        ), onItemClick = {}
    )
}