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
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.CustomSearchBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.FeatureCollectionsBar
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
    val userRequest by viewModel.userRequest.collectAsStateWithLifecycle()
    val selectedCollection by viewModel.currentFeaturedCollection.collectAsStateWithLifecycle()
    val collections by viewModel.stateOfFeaturedCollection.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    var searchBarState by remember { mutableStateOf(SearchBarState.INACTIVE) }
    val userRequestsHistory = remember { mutableListOf<String>() }

    SetStatusBarColor()

    LaunchedEffect(key1 = userRequest) {
        if (userRequest.isNotBlank()) {
            viewModel.getSearchedPhotos(userRequest)
        } else {
            viewModel.getCuratedPhotos()
        }
    }

    Column {
        CustomSearchBar(
            userRequest = userRequest,
            onQueryChanged = { viewModel.modifyTextInSearchBar(it) },
            onSearch = {
                userRequestsHistory.add(it)
                searchBarState = SearchBarState.INACTIVE
            },
            userRequestsHistory = userRequestsHistory,
            active = searchBarState == SearchBarState.ACTIVE
        ) {
            searchBarState = if (it) SearchBarState.ACTIVE else SearchBarState.INACTIVE
        }

        FeatureCollectionsBar(
            idOfSelectedCollection = selectedCollection,
            parts = collections,
            searchSelectedCollection = { title, id ->
                viewModel.modifyTextInSearchBar(title)
                viewModel.modifySelectedId(id)
                coroutineScope.launch {
                    viewModel.getSearchedPhotos(title)
                }
            })
    }
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}