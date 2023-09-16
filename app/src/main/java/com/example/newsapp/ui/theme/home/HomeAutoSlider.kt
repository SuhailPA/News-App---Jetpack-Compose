package com.example.newsapp.ui.theme.home

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.data.model.NewsTable
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalAutoSlider(
    modifier: Modifier = Modifier,
    news: LazyPagingItems<NewsTable>,
    onItemClick: (NewsTable) -> Unit
) {
    if (news.itemCount != 0) {
        val pager = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            news.itemCount - 1
        }
        HorizontalPager(state = pager, modifier.height(200.dp), reverseLayout = false) { index ->

            val pageOffSet = (pager.currentPage - index) + pager.currentPageOffsetFraction

            val imageSize by animateFloatAsState(
                targetValue = if (pageOffSet.toDouble() != 0.0) 0.75f else 1f,
                animationSpec = tween(300), label = ""
            )
            Box(modifier = Modifier.clickable { news.itemSnapshotList[index]?.let { onItemClick(it) } }) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                            shape = RoundedCornerShape(15.dp)
                            clip = true
                        },
                    error = painterResource(id = R.drawable.breaking_news_placeholder),
                    placeholder = painterResource(id = R.drawable.breaking_news_placeholder),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news[index]?.urlToImage)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = news[index]?.title.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
                        .padding(16.dp)
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                            shape = RoundedCornerShape(15.dp)
                        },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
            val key by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = key) {
                launch {
                    delay(3000)
                    with(pager) {
                        val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                        animateScrollToPage(
                            page = target,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                }
            }


        }
    }


}