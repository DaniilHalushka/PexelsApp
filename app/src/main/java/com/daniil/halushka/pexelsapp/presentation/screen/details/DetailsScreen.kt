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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.presentation.navigation.ScreenRoutes
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomProgressBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.details.CustomTopBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.details.PhotoInformation

@Composable
fun DetailsScreen(
    id: String?,
    navigationController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val photo by viewModel.choosePhoto.collectAsState()

    when (navigationController.previousBackStackEntry?.destination?.route) {
        ScreenRoutes.HomeScreen.screenType -> {
            viewModel.getHomeScreenPhoto(id?.toInt())
        }
    }

    SetStatusBarColor()

    DetailsContent(
        photo = photo,
        navigationController = navigationController
    )
}

@Composable
private fun DetailsContent(
    photo: DomainPhoto?,
    navigationController: NavController
) {
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
                RenderPhotoInformation(format = photo.src.portrait)
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
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}
