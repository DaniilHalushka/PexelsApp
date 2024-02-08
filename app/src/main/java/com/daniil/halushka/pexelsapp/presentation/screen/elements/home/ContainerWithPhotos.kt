package com.daniil.halushka.pexelsapp.presentation.screen.elements.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.daniil.halushka.pexelsapp.R
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto

@Composable
fun GridWithPhotos(
    photosInContainer: List<DomainPhoto>,
    clickByPhoto: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 64.dp)
    ) {
        items(photosInContainer) {
            PhotoInGrid(
                id = it.id,
                format = it.src.medium,
                onClick = clickByPhoto
            )
        }
    }
}

@Composable
fun PhotoInGrid(
    id: Int,
    format: String,
    onClick: (String) -> Unit = {}
) {
    val pictureInPlaceholder = painterResource(
        id = if (isSystemInDarkTheme()) R.drawable.ic_placeholder_dark
        else R.drawable.ic_placeholder_light
    )

    AsyncImage(
        model = format,
        contentDescription = "Photo",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick(id.toString())
            },
        placeholder = pictureInPlaceholder
    )
}