package com.daniil.halushka.pexelsapp.presentation.navigation

import com.daniil.halushka.pexelsapp.R

sealed class ScreenRoutes(
    val screenType: String,
    val activeIcon: Int? = null,
    val inactiveIcon: Int? = null,
) {
    data object SplashScreen : ScreenRoutes(
        screenType = "Splash"
    )

    data object HomeScreen : ScreenRoutes(
        screenType = "Home",
        activeIcon = R.drawable.ic_home_button_active,
        inactiveIcon = R.drawable.ic_home_button_inactive
    )

    data object DetailsScreen : ScreenRoutes(
        screenType = "Details/{id}"
    )

    data object BookmarksScreen : ScreenRoutes(
        screenType = "Bookmarks",
        activeIcon = R.drawable.ic_bookmark_button_active,
        inactiveIcon = R.drawable.ic_bookmark_button_inactive,
    )
}