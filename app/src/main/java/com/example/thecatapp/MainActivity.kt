package com.example.thecatapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thecatapp.cat.CatScreen
import com.example.thecatapp.cat.CatViewModel
import com.example.thecatapp.catDescription.CatDescriptionScreen
import com.example.thecatapp.navigation.CatDescriptionNavigationEvents
import com.example.thecatapp.navigation.CatNavigationRoutes.CAT_SCREEN_ROUTE
import com.example.thecatapp.navigation.CatNavigationRoutes.DESCRIPTION_SCREEN_ROUTE
import com.example.thecatapp.navigation.CatNavigationRoutes.DESCRIPTION_SCREEN_ROUTE_TEMPLATE
import com.example.thecatapp.navigation.NavigationEvents
import com.example.thecatapp.ui.theme.TheCatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCatAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = CAT_SCREEN_ROUTE) {
                    composable(route = CAT_SCREEN_ROUTE) {
                        val viewModel by viewModels<CatViewModel>()
                        val catState = viewModel.catStateFlow.collectAsStateWithLifecycle()
                        CatScreen(catState.value, viewModel::setEvents) { navigationEvents ->
                            when (navigationEvents) {
                                is NavigationEvents.OpenDescription -> {
                                    navController.navigate(
                                        DESCRIPTION_SCREEN_ROUTE_TEMPLATE.format(
                                            URLEncoder.encode(
                                                navigationEvents.breeData.image?.url.orEmpty(),
                                                StandardCharsets.UTF_8.toString()
                                            ),
                                            navigationEvents.breeData.name,
                                            navigationEvents.breeData.description.orEmpty(),
                                            navigationEvents.breeData.lifeSpan.orEmpty(),
                                            navigationEvents.breeData.origin.orEmpty(),
                                            navigationEvents.breeData.temperament.orEmpty(),
                                            URLEncoder.encode(
                                                navigationEvents.breeData.wikiUrl.orEmpty(),
                                                StandardCharsets.UTF_8.toString()
                                            ),
                                        )
                                    )
                                }
                            }
                        }
                    }

                    composable(
                        route = DESCRIPTION_SCREEN_ROUTE,
                        arguments = listOf(
                            navArgument(name = "imageUrl") {
                                type = NavType.StringType
                            },
                            navArgument(name = "name") {
                                type = NavType.StringType
                            },
                            navArgument(name = "description") {
                                type = NavType.StringType
                            },
                            navArgument(name = "lifeSpan") {
                                type = NavType.StringType
                            },
                            navArgument(name = "origin") {
                                type = NavType.StringType
                            },
                            navArgument(name = "wiki") {
                                type = NavType.StringType
                            },
                            navArgument(name = "temperament") {
                                type = NavType.StringType
                            })
                    ) { navBackStackEntry ->
                        val image = navBackStackEntry.arguments?.getString("imageUrl")
                        val name = navBackStackEntry.arguments?.getString("name")
                        val description = navBackStackEntry.arguments?.getString("description")
                        val origin = navBackStackEntry.arguments?.getString("origin")
                        val temperament = navBackStackEntry.arguments?.getString("temperament")
                        val lifeSpan = navBackStackEntry.arguments?.getString("lifeSpan")
                        val wikiUrl = navBackStackEntry.arguments?.getString("wiki")
                        CatDescriptionScreen(
                            image = image,
                            name = name,
                            description = description,
                            origin = origin,
                            temperament = temperament,
                            lifeSpan = lifeSpan,
                            wikiUrl = wikiUrl,
                        ) { navigationEvents ->
                            when (navigationEvents) {

                                is CatDescriptionNavigationEvents.OpenWikiUrl -> {
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(navigationEvents.url))
                                    startActivity(intent)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}