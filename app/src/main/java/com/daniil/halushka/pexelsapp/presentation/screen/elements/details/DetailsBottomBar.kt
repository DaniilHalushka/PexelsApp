package com.daniil.halushka.pexelsapp.presentation.screen.elements.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.daniil.halushka.pexelsapp.R

@Composable
fun DetailsBottomBar(
    addPhotoToBookmarks: Boolean,
    downloadButtonClick: () -> Unit,
    bookmarkButtonClick: () -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DownloadButton(
            onClick = downloadButtonClick,
            backgroundColor = primaryColor,
            contentColor = tertiaryColor
        )

        BookmarksButton(
            onClick = bookmarkButtonClick,
            addPhotoToBookmarks = addPhotoToBookmarks,
            backgroundColor = primaryColor,
            contentColor = if (addPhotoToBookmarks) tertiaryColor else secondaryColor
        )
    }
}

@Composable
private fun DownloadButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .width((screenWidth - 48.dp) / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(contentColor)
                .size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = stringResource(id = R.string.download_icon_description),
                tint = Color.White
            )
        }
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.download),
                textAlign = TextAlign.Center
            )
    }
}

@Composable
private fun BookmarksButton(
    onClick: () -> Unit,
    addPhotoToBookmarks: Boolean,
    backgroundColor: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = if (addPhotoToBookmarks) R.drawable.ic_bookmark_button_active
                else R.drawable.ic_bookmark_button_inactive
            ),
            contentDescription = stringResource(id = R.string.bookmarks_button_description),
            tint = contentColor
        )
    }
}