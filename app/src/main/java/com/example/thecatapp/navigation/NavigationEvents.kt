package com.example.thecatapp.navigation

import com.example.thecatapp.cat.models.Breed

sealed interface NavigationEvents {
    data class OpenDescription(val breeData: Breed) : NavigationEvents
}