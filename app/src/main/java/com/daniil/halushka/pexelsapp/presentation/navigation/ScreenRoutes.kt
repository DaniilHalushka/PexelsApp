package com.daniil.halushka.pexelsapp.presentation.navigation

import com.daniil.halushka.pexelsapp.R

enum class ScreenType {
    SPLASH,
    HOME,
    DETAILS,
    BOOKMARKS
}

sealed class ScreenRoutes(
    val screenType: ScreenType,
    val activeIcon: Int? = null,
    val inactiveIcon: Int? = null,
) {
    data object SplashScreen : ScreenRoutes(
        ScreenType.SPLASH
    )

    data object HomeScreen : ScreenRoutes(
        ScreenType.HOME,
        activeIcon = R.drawable.ic_home_button_active,
        inactiveIcon = R.drawable.ic_home_button_inactive
    )

    data object DetailsScreen : ScreenRoutes(
        ScreenType.DETAILS
    )

    data object BookmarksScreen : ScreenRoutes(
        ScreenType.BOOKMARKS,
        activeIcon = R.drawable.ic_bookmark_button_active,
        inactiveIcon = R.drawable.ic_bookmark_button_inactive,

    )
}