package com.daniil.halushka.pexelsapp.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daniil.halushka.pexelsapp.presentation.screen.bookmarks.BookmarksScreen
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
            ScreenRoutes.SplashScreen.screenType.name,
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
            ScreenRoutes.HomeScreen.screenType.name,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(1000)
                )
            }
        ) {
            HomeScreen(navigationController = navController)
        }
        composable(
            ScreenRoutes.BookmarksScreen.screenType.name,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            BookmarksScreen(navigationController = navController)
        }
    }
}
