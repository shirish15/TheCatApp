package com.example.thecatapp.navigation

object CatNavigationRoutes {
    const val CAT_SCREEN_ROUTE = "main_screen_route"
    const val DESCRIPTION_SCREEN_ROUTE_TEMPLATE = "description_screen_route/%s/%s/%s/%s/%s/%s/%s"
    const val DESCRIPTION_SCREEN_ROUTE =
        "description_screen_route/{imageUrl}/{name}/{description}/{lifeSpan}/{origin}/{temperament}/{wiki}"
}