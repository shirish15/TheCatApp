package com.example.thecatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, navigationIcon: Int,color: Color) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color,
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            Image(
                rememberAsyncImagePainter(navigationIcon),
                modifier = Modifier.size(40.dp),
                contentDescription = null
            )
        }
    )
}