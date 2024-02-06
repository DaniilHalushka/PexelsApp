package com.daniil.halushka.pexelsapp.presentation.screen.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.daniil.halushka.pexelsapp.R
import com.daniil.halushka.pexelsapp.presentation.navigation.ScreenRoutes

fun isScreenActive(screenRouteNavigation: NavDestination?, screenRoute: ScreenRoutes): Boolean {
    return screenRouteNavigation?.hierarchy?.any {
        it.route == screenRoute.screenType.name
    } == true
}

fun navigateToScreen(navigationController: NavHostController, screenType: String) {
    navigationController.navigate(screenType) {
        popUpTo(navigationController.graph.startDestinationId) {
            inclusive = true
        }
    }
}

@Composable
fun CustomBottomBar(navigationController: NavHostController) {
    val navigationBackStackEntry by navigationController.currentBackStackEntryAsState()
    val screenRouteNavigation = navigationBackStackEntry?.destination

    val bottomBarScreens = setOf(ScreenRoutes.HomeScreen, ScreenRoutes.BookmarksScreen)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        bottomBarScreens.forEach { bottomBarScreen ->
            CustomBottomBarNavigation(
                screenRouteNavigation = screenRouteNavigation,
                bottomBarScreen = bottomBarScreen,
                navigationController = navigationController
            )
        }
    }
}

@Composable
fun CustomBottomBarNavigation(
    screenRouteNavigation: NavDestination?,
    bottomBarScreen: ScreenRoutes,
    navigationController: NavHostController
) {
    val activeScreenInBottomBar = isScreenActive(screenRouteNavigation, bottomBarScreen)
    val colorOfActiveScreenInBottomBar =
        if (activeScreenInBottomBar) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary

    Box(
        modifier = Modifier
            .size(64.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .clickable(onClick = {
                    navigateToScreen(navigationController, bottomBarScreen.screenType.name)
                }),
            contentAlignment = Alignment.Center
        ) {
            val iconResource = if (activeScreenInBottomBar) bottomBarScreen.activeIcon
            else bottomBarScreen.inactiveIcon
            iconResource?.let {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = it),
                    contentDescription = stringResource(id = R.string.custom_bottom_bar_icon_description),
                    tint = colorOfActiveScreenInBottomBar
                )
            }
        }
        AnimatedVisibility(visible = activeScreenInBottomBar) {
            Divider(
                modifier = Modifier.size(24.dp, 2.dp),
                color = colorOfActiveScreenInBottomBar
            )
        }
    }
}