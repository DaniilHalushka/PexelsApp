package com.daniil.halushka.pexelsapp.presentation.screen.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBar(
    progress: Boolean
) {
    AnimatedVisibility(
        visible = progress,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { -it })
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary,

            )
    }
}