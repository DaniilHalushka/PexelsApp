package com.daniil.halushka.pexelsapp.presentation.screen.splash

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.R
import com.daniil.halushka.pexelsapp.presentation.navigation.ScreenRoutes
import com.daniil.halushka.pexelsapp.ui.theme.redColor
import com.daniil.halushka.pexelsapp.ui.theme.whiteColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigationController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaValue by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000),
        label = "Splash Screen Animation"
    )

    SetStatusBarColor()

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navigationController.navigate(
            route = ScreenRoutes.HomeScreen.screenType.name
        ) {
            popUpTo(ScreenRoutes.SplashScreen.screenType.name)
        }
    }

    SplashScreenContent(alphaValue = alphaValue)
}

@Composable
private fun SplashScreenContent(
    alphaValue: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(redColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.alpha(alpha = alphaValue),
            contentAlignment = Alignment.BottomEnd
        ) {
            SplashScreenImage(alphaValue)
            SplashScreenText()
        }
    }
}

@Composable
private fun SplashScreenImage(alphaValue: Float) {
    Image(
        alpha = alphaValue,
        painter = painterResource(id = R.drawable.ic_app_logo),
        contentDescription = stringResource(id = R.string.splash_screen_image_info)
    )
}

@Composable
private fun SplashScreenText() {
    Text(
        text = stringResource(id = R.string.splash_screen_text),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = whiteColor,
        textAlign = TextAlign.End
    )
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.tertiary.toArgb()
}