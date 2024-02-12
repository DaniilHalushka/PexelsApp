package com.daniil.halushka.pexelsapp.presentation.screen.bookmarks

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.R
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomProgressBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomTopBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.bookmarks.GridWithBookmarks

@Composable
fun BookmarksScreen(
    navigationController: NavController,
    viewModel: BookmarksScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadBookmarkPhotos()
    }

    val bookmarkPhotos by viewModel.bookmarkPhotos.collectAsStateWithLifecycle()

    SetStatusBarColor()

    Scaffold(
        topBar = {
            CustomTopBar(
                author = stringResource(id = R.string.bookmarks),
                isNavigationExist = false
            )
        }
    ) { actualPadding ->
        Column(
            modifier = Modifier
                .padding(actualPadding)
                .fillMaxSize()
        ) {
            RenderCustomProgressBar(progress = bookmarkPhotos.isEmpty())
            if (bookmarkPhotos.isNotEmpty()) {
                RenderGridWithBookmarks(
                    bookmarkPhotos = bookmarkPhotos,
                    navigationController = navigationController
                )
            }
        }
    }
}

@Composable
private fun RenderCustomProgressBar(
    progress: Boolean
) {
    CustomProgressBar(
        progress = progress
    )
}

@Composable
private fun RenderGridWithBookmarks(
    bookmarkPhotos: List<DomainPhoto>,
    navigationController: NavController
) {
    GridWithBookmarks(
        bookmarksInContainer = bookmarkPhotos,
        clickByPhoto = {
            navigationController.navigate("details/$it")
        }
    )
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}