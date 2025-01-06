package com.example.thecatapp.navigation

sealed interface CatDescriptionNavigationEvents {
    data class OpenWikiUrl(val url: String) : CatDescriptionNavigationEvents
}