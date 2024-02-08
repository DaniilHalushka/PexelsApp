package com.daniil.halushka.pexelsapp.presentation.screen.home

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomProgressBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.CustomSearchBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.FeatureCollectionsBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.GridWithPhotos
import kotlinx.coroutines.launch

enum class SearchBarState {
    ACTIVE,
    INACTIVE
}

@Composable
fun HomeScreen(
    navigationController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val selectedCollection by viewModel.currentFeaturedCollection.collectAsStateWithLifecycle()
    val collections by viewModel.stateOfFeaturedCollection.collectAsStateWithLifecycle()
    val photoList by viewModel.photos.collectAsStateWithLifecycle()

    SetStatusBarColor()

    Column {
        RenderSearchBar(viewModel = viewModel)
        RenderFeatureCollectionsBar(
            selectedCollection = selectedCollection,
            collections = collections,
            viewModel = viewModel
        )

        RenderCustomProgressBar(
            progress = photoList.isEmpty()
        )

        RenderGridWithPhotos(
            photoList = photoList,
            navigationController = navigationController
        )
    }
}

@Composable
private fun RenderSearchBar(viewModel: HomeScreenViewModel) {
    var searchBarState by remember { mutableStateOf(SearchBarState.INACTIVE) }
    val userRequest by viewModel.userRequest.collectAsStateWithLifecycle()
    val userRequestsHistory = remember { mutableListOf<String>() }

    LaunchedEffect(key1 = userRequest) {
        if (userRequest.isNotBlank()) {
            viewModel.getSearchedPhotos(userRequest)
        } else {
            viewModel.getCuratedPhotos()
        }
    }

    CustomSearchBar(
        userRequest = userRequest,
        active = searchBarState == SearchBarState.ACTIVE,
        onQueryChanged = { viewModel.modifyTextInSearchBar(it) },
        onSearch = {
            userRequestsHistory.add(it)
            searchBarState = SearchBarState.INACTIVE
        },
        userRequestsHistory = userRequestsHistory,
    ) {
        searchBarState = if (it) SearchBarState.ACTIVE else SearchBarState.INACTIVE
    }
}

@Composable
private fun RenderFeatureCollectionsBar(
    selectedCollection: String,
    collections: List<DomainFeaturedCollection>,
    viewModel: HomeScreenViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    FeatureCollectionsBar(
        idOfSelectedCollection = selectedCollection,
        parts = collections,
        searchSelectedCollection = { title, id ->
            viewModel.modifyTextInSearchBar(title)
            viewModel.modifySelectedId(id)
            coroutineScope.launch {
                viewModel.getSearchedPhotos(title)
            }
        }
    )
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
private fun RenderGridWithPhotos(
    photoList: List<DomainPhoto>,
    navigationController: NavController
) {
    GridWithPhotos(
        photosInContainer = photoList,
        clickByPhoto = {
            navigationController.navigate(
                "Details/$it"
            )
        }
    )
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}
