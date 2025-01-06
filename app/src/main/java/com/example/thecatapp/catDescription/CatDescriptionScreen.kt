package com.example.thecatapp.catDescription

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.thecatapp.R
import com.example.thecatapp.navigation.CatDescriptionNavigationEvents
import com.example.thecatapp.utils.LinkItem
import com.example.thecatapp.utils.TopBar
import com.example.thecatapp.utils.buildAnnotatedString

@Composable
fun CatDescriptionScreen(
    image: String?,
    name: String?,
    description: String?,
    origin: String?,
    temperament: String?,
    lifeSpan: String?,
    wikiUrl: String?,
    navigate: (CatDescriptionNavigationEvents) -> Unit,
) {
    Scaffold(topBar = {
        TopBar(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            title = name ?: "Some or the other cat",
            navigationIcon = R.drawable.cat_face_with_wry_smile_svgrepo_com
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.Black)
        ) {
            Image(
                painter = rememberAsyncImagePainter(image),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Cat Image"
            )
            origin?.let {
                Text(
                    text = buildAnnotatedString("Origin", it),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            lifeSpan?.let {
                Text(
                    text = buildAnnotatedString("Life Span", it),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            temperament?.let {
                Text(
                    text = buildAnnotatedString("Temperament", it),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            description?.let {
                Text(
                    text = buildAnnotatedString("Description", it),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            wikiUrl?.let {
                LinkItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp), linkText =
                    "Know more about this catttt.."
                ) {
                    navigate(CatDescriptionNavigationEvents.OpenWikiUrl(wikiUrl))
                }
            }
        }
    }
}