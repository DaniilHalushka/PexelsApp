package com.daniil.halushka.pexelsapp.presentation.screen.home

import android.app.Activity
import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.daniil.halushka.pexelsapp.R
import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.presentation.screen.elements.CustomProgressBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.CustomSearchBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.FeatureCollectionsBar
import com.daniil.halushka.pexelsapp.presentation.screen.elements.home.GridWithPhotos
import com.daniil.halushka.pexelsapp.presentation.screen.utils.FailedResultScreen
import com.daniil.halushka.pexelsapp.presentation.screen.utils.checkInternetConnection
import kotlinx.coroutines.CoroutineScope
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
    val activeError by viewModel.activeError.collectAsStateWithLifecycle()
    val userRequest by viewModel.userRequest.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    SetStatusBarColor()

    CheckAndShowInternetAlert(context, viewModel)

    RenderContent(
        selectedCollection = selectedCollection,
        collections = collections,
        photoList = photoList,
        navigationController = navigationController,
        viewModel = viewModel,
        activeError = activeError,
        coroutineScope = coroutineScope,
        userRequest = userRequest,
        onSearchBarStateChanged = {},
        onSearch = {},
        hasInternet = checkInternetConnection(context = context)
    )
}


@Composable
private fun RenderContent(
    selectedCollection: String,
    collections: List<DomainFeaturedCollection>,
    photoList: List<DomainPhoto>,
    navigationController: NavController,
    viewModel: HomeScreenViewModel,
    activeError: Boolean,
    coroutineScope: CoroutineScope,
    userRequest: String,
    onSearchBarStateChanged: (SearchBarState) -> Unit,
    onSearch: (String) -> Unit,
    hasInternet: Boolean
) {
    Column {
        if (hasInternet) {
            RenderSearchBar(
                viewModel = viewModel,
                userRequest = userRequest,
                onSearchBarStateChanged = onSearchBarStateChanged,
                onSearch = onSearch
            )
            RenderFeatureCollectionsBar(
                selectedCollection = selectedCollection,
                collections = collections,
                viewModel = viewModel,
                coroutineScope = coroutineScope
            )
            RenderCustomProgressBar(progress = photoList.isEmpty())
            if (!activeError && photoList.isNotEmpty()) {
                RenderGridWithPhotos(
                    photoList = photoList,
                    navigationController = navigationController
                )
            } else if (activeError) {
                RenderFailedResultScreen(
                    result = stringResource(id = R.string.no_results_found),
                    onRetryClicked = {
                        coroutineScope.launch {
                            viewModel.getCuratedPhotos()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RenderSearchBar(
    viewModel: HomeScreenViewModel,
    userRequest: String,
    onSearchBarStateChanged: (SearchBarState) -> Unit,
    onSearch: (String) -> Unit
) {
    var searchBarState by remember { mutableStateOf(SearchBarState.INACTIVE) }
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
            onSearch(it)
        },
        userRequestsHistory = userRequestsHistory,
    ) {
        searchBarState = if (it) SearchBarState.ACTIVE else SearchBarState.INACTIVE
        onSearchBarStateChanged(searchBarState)
    }
}

@Composable
private fun RenderFeatureCollectionsBar(
    selectedCollection: String,
    collections: List<DomainFeaturedCollection>,
    viewModel: HomeScreenViewModel,
    coroutineScope: CoroutineScope
) {
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
private fun RenderHomeEmptyScreen(onRetryClicked: () -> Unit) {
    HomeEmptyScreen(onClick = onRetryClicked)
}

@Composable
private fun RenderFailedResultScreen(
    result: String,
    onRetryClicked: () -> Unit
) {
    FailedResultScreen(
        result = result,
        onClick = onRetryClicked
    )
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Composable
fun CheckAndShowInternetAlert(
    context: Context,
    viewModel: HomeScreenViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    if (!checkInternetConnection(context)) {
        showToast(
            context,
            context.getString(R.string.check_your_internet_connection)
        )
        RenderHomeEmptyScreen(
            onRetryClicked = {
                coroutineScope.launch {
                    viewModel.getCuratedPhotos()
                    viewModel.getFeaturedCollections()
                }
            }
        )
    }
}

@Composable
private fun SetStatusBarColor() {
    val activity = (LocalContext.current as Activity)
    val window = activity.window

    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
}