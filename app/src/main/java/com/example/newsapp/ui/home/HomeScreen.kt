package com.example.newsapp.ui.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsTable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClick: (NewsTable) -> Unit,
    news: LazyPagingItems<NewsTable>
) {
    PermissionCheck()
    val context = LocalContext.current
    LaunchedEffect(key1 = news.loadState) {
        if (news.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                (news.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (news.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            NewsList(news = news, onItemClick = onItemClick)
        }
    }
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionCheck() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        val permissionState =
            rememberPermissionState(
                permission = Manifest.permission.POST_NOTIFICATIONS
            )
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = lifecycleOwner, effect = {
            val eventObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        permissionState.launchPermissionRequest()
                    }

                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(eventObserver)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(eventObserver)
            }
        })

        when {
            permissionState.status.shouldShowRationale -> {
                ExplainingUserForPermissionRequirement()
            }

            !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
                Snackbar(modifier = Modifier.padding(5.dp)) {
                    Text(text = "No Notifications will be sent to you.")
                }
            }
        }
    }
}

@Composable
fun ExplainingUserForPermissionRequirement() {
    Card {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "For getting notification you may need to allow the permission from Settings"
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
    news: LazyPagingItems<NewsTable>,
    onItemClick: (NewsTable) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (news.itemSnapshotList.items.isNotEmpty()) {
            item {
                HorizontalAutoSlider(news = news, onItemClick = onItemClick)
            }

            items(items = news.itemSnapshotList.items) {

                NewsItem(news = it, onItemClick = onItemClick)

            }
        }
    }
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