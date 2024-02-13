package com.daniil.halushka.pexelsapp.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daniil.halushka.pexelsapp.presentation.screen.bookmarks.BookmarksScreen
import com.daniil.halushka.pexelsapp.presentation.screen.details.DetailsScreen
import com.daniil.halushka.pexelsapp.presentation.screen.home.HomeScreen
import com.daniil.halushka.pexelsapp.presentation.screen.splash.SplashScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController,
        startDestination = startDestination,
    ) {
        composable(
            ScreenRoutes.SplashScreen.screenType,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(1000)
                )
            }
        ) {
            SplashScreen(navigationController = navController)
        }
        composable(
            ScreenRoutes.HomeScreen.screenType,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(1500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(1500)
                )
            }
        ) {
            HomeScreen(navigationController = navController)
        }
        composable(
            ScreenRoutes.BookmarksScreen.screenType,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(1500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(1500)
                )
            }
        ) {
            BookmarksScreen(navigationController = navController)
        }
        composable(
            ScreenRoutes.DetailsScreen.screenType,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(1000)
                )
            }
        ) { screen ->
            val arguments = requireNotNull(screen.arguments)
            DetailsScreen(
                navigationController = navController,
                id = arguments.getString("id")
            )
        }
    }
}
