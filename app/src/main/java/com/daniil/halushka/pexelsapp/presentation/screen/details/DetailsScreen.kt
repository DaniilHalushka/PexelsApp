package com.daniil.halushka.pexelsapp.presentation.screen.details

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.data.download.DownloadImageImplementation
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.presentation.navigation.ScreenRoutes
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomProgressBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomTopBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.details.DetailsBottomBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.details.PhotoInformation

@Composable
fun DetailsScreen(
    id: String?,
    navigationController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {

    when (navigationController.previousBackStackEntry?.destination?.route) {
        ScreenRoutes.HomeScreen.screenType -> {
            viewModel.getHomeScreenPhoto(id?.toInt())
        }

        ScreenRoutes.BookmarksScreen.screenType -> {
            viewModel.getPhotoFromBookmarksScreen(id?.toInt())
        }
    }

    SetStatusBarColor()

    DetailsContent(
        navigationController = navigationController,
        viewModel = viewModel
    )
}

@Composable
private fun DetailsContent(
    navigationController: NavController,
    viewModel: DetailsScreenViewModel
) {
    val photo by viewModel.choosePhoto.collectAsStateWithLifecycle()
    val addPhotoToBookmark by viewModel.addPhotoToBookmarks.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        topBar = {
            CustomTopBar(
                author = photo?.photographer ?: "",
                isNavigationExist = true,
                clickOnNavigationItem = {
                    navigationController.popBackStack()
                }
            )
        }
    ) { actualPadding ->
        Column(
            modifier = Modifier
                .padding(actualPadding)
                .verticalScroll(rememberScrollState())
        ) {
            RenderCustomProgressBar(photo == null)

            if (photo != null) {
                RenderPhotoInformation(format = photo?.src?.portrait)

                RenderDetailsBottomBar(
                    photo = photo!!,
                    viewModel = viewModel,
                    addPhotoToBookmark = addPhotoToBookmark,
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
private fun RenderPhotoInformation(format: String?) {
    PhotoInformation(format = format)
}

@Composable
private fun RenderDetailsBottomBar(
    addPhotoToBookmark: Boolean,
    viewModel: DetailsScreenViewModel,
    photo: DomainPhoto
) {
    val imageDownloader = DownloadImageImplementation(LocalContext.current)

    DetailsBottomBar(
        addPhotoToBookmarks = addPhotoToBookmark,
        downloadButtonClick = {
            imageDownloader.downloadFile(
                photo.src.original,
                photo.alt.trim()
            )
        },
        bookmarkButtonClick = {
            viewModel.changeStateOnBookmarksButton(photo)
        }
    )
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}
