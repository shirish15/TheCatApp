package com.example.thecatapp.navigation

import com.example.network.models.Breed

sealed interface NavigationEvents {
    data class OpenDescription(val breeData: Breed) : NavigationEvents
}