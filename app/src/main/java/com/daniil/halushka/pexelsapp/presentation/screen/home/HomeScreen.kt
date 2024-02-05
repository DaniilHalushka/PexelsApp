package com.daniil.halushka.pexelsapp.presentation.screen.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.R

@Composable
fun HomeScreen(
    navigationController: NavController
) {
    SetStatusBarColor()

    //TEST ANIMATION BETWEEN SCREENS
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_bookmark_button_active),
            contentDescription = stringResource(id = R.string.splash_screen_image_info)
        )
        Text(
            text = "Test text"
        )
    }
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}