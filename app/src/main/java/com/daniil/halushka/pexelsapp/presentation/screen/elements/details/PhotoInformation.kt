package com.daniil.halushka.pexelsapp.presentation.screen.elements.details

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.daniil.halushka.pexelsapp.R

@Composable
fun PhotoInformation(
    format: String?
) {
    if (format != null) {
        LoadPhoto(format)
    }
}

@Composable
private fun getPlaceholder() =
    painterResource(
        id = if (isSystemInDarkTheme()) R.drawable.ic_placeholder_dark
        else R.drawable.ic_placeholder_light
    )

@Composable
private fun LoadPhoto(
    format: String
) {
    AsyncImage(
        model = format,
        contentDescription = stringResource(id = R.string.photo_information),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .clip(RoundedCornerShape(24.dp)),
        placeholder = getPlaceholder()
    )
}
