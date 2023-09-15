package com.example.newsapp.ui.theme.detail

import android.graphics.Color.alpha
import android.graphics.Paint.Align
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.newsapp.data.model.NewsUiState
import com.example.newsapp.ui.theme.NewsTopAppBar

import com.example.newsapp.ui.theme.navigation.NewsAppScreens
import okhttp3.internal.wait


@Composable
fun DetailScreen(
    newsUiState: NewsUiState,
    navigateUp: () -> Unit,
    addedToFavorite: (Boolean) -> Unit
) {
    BackHandler {
        navigateUp()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsUiState.selectedItem.urlToImage)
                    .build(),
                error = painterResource(id = R.drawable.breaking_news_placeholder),
                placeholder = painterResource(id = R.drawable.breaking_news_placeholder),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .clip(shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 15.dp)),
                contentScale = ContentScale.Crop
            )
            NewsTimeAndSource(newsItem = newsUiState.selectedItem, modifier = Modifier.weight(0.5f))
            TextDescription(
                newState = newsUiState.selectedItem,
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Bottom)
            )
        }
        HeaderPart(navigateUp = navigateUp, isFavourite = newsUiState.selectedItem.favourite, addedToFavorite = addedToFavorite)
    }
}


@Composable
fun HeaderPart(modifier: Modifier = Modifier, navigateUp: () -> Unit, isFavourite: Boolean, addedToFavorite: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { navigateUp() },
            modifier = Modifier
                .statusBarsPadding()
                .padding(15.dp)
                .size(36.dp)
                .background(
                    color = Color.White.copy(alpha = 0.75f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "BackButton",
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { addedToFavorite(!isFavourite) },
            modifier = Modifier
                .statusBarsPadding()
                .padding(15.dp)
                .size(36.dp)
                .background(
                    color = Color.White.copy(alpha = 0.75f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Icon(
                imageVector = if (isFavourite) Icons.Default.Favorite
                else Icons.Default.FavoriteBorder,
                contentDescription = "BackButton",
                modifier = Modifier
            )
        }
    }
}

@Composable
fun NewsTimeAndSource(newsItem: NewsTable, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = newsItem.author ?: "Author Unspecified",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = newsItem.publishedAt ?: "Time Unspecified",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun TextDescription(modifier: Modifier = Modifier, newState: NewsTable) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)

    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            item {
                Text(
                    text = newState.title.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = newState.description.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = newState.content.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                )
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewDetailScreen() {
//    DetailScreen(
//        newsItem = NewsTable(
//            id = 0,
//            description = LocalContext.current.getString(R.string.dummy_description),
//            author = "Suhail P A",
//            content = "",
//            publishedAt = "",
//            title = "TestTitle",
//            url = "",
//            urlToImage = ""
//        ),
//        {},{})
//}
