package com.daniil.halushka.pexelsapp.presentation.screen.elements.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.daniil.halushka.pexelsapp.R
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto

@Composable
fun GridWithBookmarks(
    bookmarksInContainer: List<DomainPhoto>,
    clickByPhoto: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 64.dp)
    ) {
        items(bookmarksInContainer) {
            BookmarkInGrid(
                id = it.id,
                author = it.photographer ?: stringResource(id = R.string.name_surname),
                format = it.src.medium,
                onClick = clickByPhoto
            )
        }
    }
}

@Composable
fun BookmarkInGrid(
    id: Int,
    author: String,
    format: String,
    onClick: (String) -> Unit = {}
) {
    val pictureInPlaceholder = painterResource(
        id = if (isSystemInDarkTheme()) R.drawable.ic_placeholder_dark
        else R.drawable.ic_placeholder_light
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = format,
            contentDescription = "Photo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .clickable {
                    onClick(id.toString())
                },
            placeholder = pictureInPlaceholder
        )
        AuthorTextBlock(author = author)
    }
}

@Composable
fun AuthorTextBlock(author: String) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .background(Color.Black.copy(0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = author,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.White
        )
    }
}