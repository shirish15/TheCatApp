package com.example.thecatapp.utils

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun buildAnnotatedString(title: String, desc: String): AnnotatedString {
    return androidx.compose.ui.text.buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
            append("$title: ")
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiaryContainer)) {
            append(desc)
        }
    }
}

@Composable
fun LinkItem(modifier: Modifier = Modifier, linkText: String, onClick: () -> Unit) {
    Text(
        text = linkText,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        textDecoration = TextDecoration.Underline,
        modifier = modifier.clickable(onClick = onClick)
    )
}