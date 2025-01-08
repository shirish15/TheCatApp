package com.example.thecatapp.cat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.network.models.Breed
import com.example.thecatapp.R
import com.example.thecatapp.navigation.NavigationEvents
import com.example.thecatapp.ui.theme.Typography
import com.example.thecatapp.utils.TopBar

@Composable
fun CatScreen(
    catState: CatState,
    setEvents: (CatEvents) -> Unit,
    navigate: (NavigationEvents) -> Unit
) {
    Scaffold(modifier = Modifier.background(Color.Black), topBar = {
        TopBar(
            title = "Too Many Cats",
            color = MaterialTheme.colorScheme.primaryContainer,
            navigationIcon = R.drawable.toolbar_cat
        )
    }) { padding ->
        val scrollState = rememberLazyListState()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pagingnation_cat))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (catState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            if (!catState.errorState.isNullOrEmpty()) {
                Text(
                    catState.errorState,
                    style = MaterialTheme.typography.bodyLarge.copy(Color.Red)
                )
            }
            LaunchedEffect(scrollState.canScrollForward) {
                if (catState.hasMoreItems && !scrollState.canScrollForward) {
                    setEvents(CatEvents.LoadMoreData)
                }
            }
            if (!catState.catImagesList.isNullOrEmpty()) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(catState.catImagesList.size) {
                        CatItemComposable(
                            index = it,
                            catData = catState.catImagesList[it]
                        ) { item ->
                            navigate(NavigationEvents.OpenDescription(item))
                        }
                    }
                    if (catState.isPagingInProgress || !catState.pagingError.isNullOrEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (catState.isPagingInProgress) {
                                    LottieAnimation(
                                        composition = composition,
                                        modifier = Modifier,
                                        progress = { progress },
                                        contentScale = ContentScale.FillWidth
                                    )
                                }
                                catState.pagingError?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.fillMaxWidth(),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color.Red,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatItemComposable(index: Int, catData: Breed, onClick: (Breed) -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader_cat))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
            .clickable(onClick = {
                onClick(catData)
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val painter = rememberAsyncImagePainter(model = catData.image?.url)
        val state by painter.state.collectAsState()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 320.dp)
        ) {
            when (state) {
                is AsyncImagePainter.State.Loading -> {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(100.dp),
                        progress = { progress }
                    )
                }

                else -> {
                    Image(
                        painter = if (state is AsyncImagePainter.State.Error) painterResource(R.drawable.toolbar_cat) else painter,
                        contentDescription = "Cat image $index",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
                    )
                }
            }
        }

        Text(
            text = catData.name,
            style = Typography.bodyLarge.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
    }
}