package com.daniil.halushka.pexelsapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniil.halushka.pexelsapp.constants.Constants.PHOTOS_PER_PAGE
import com.daniil.halushka.pexelsapp.constants.Constants.QUANTITY_OF_COLLECTIONS
import com.daniil.halushka.pexelsapp.constants.Constants.START_PAGE
import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.repository.NetworkRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val networkRepositoryInterface: NetworkRepositoryInterface
) : ViewModel() {
    val photos = MutableStateFlow<List<DomainPhoto>>(listOf())
    val stateOfFeaturedCollection = MutableStateFlow<List<DomainFeaturedCollection>>(listOf())

    val userRequest = MutableStateFlow("")
    val currentFeaturedCollection = MutableStateFlow("")

    init {
        viewModelScope.launch {
            getFeaturedCollections()
            getCuratedPhotos()
        }
    }

    fun modifySelectedId(id: String) {
        currentFeaturedCollection.value = id
    }

    fun modifyTextInSearchBar(request: String) {
        userRequest.value = request
        currentFeaturedCollection.value = ""
    }

    suspend fun getSearchedPhotos(
        userSearch: String
    ) {
        val listOfSearchedPhotos = networkRepositoryInterface
            .getPhotosBySearch(
                query = userSearch,
                page = START_PAGE,
                perPage = PHOTOS_PER_PAGE
            )
        photos.emit(listOfSearchedPhotos)
    }

    suspend fun getCuratedPhotos() {
        val listOfCuratedPhotos = networkRepositoryInterface
            .getCuratedPhotos(
                page = START_PAGE,
                perPage = PHOTOS_PER_PAGE
            )
        photos.emit(listOfCuratedPhotos)
    }

    suspend fun getFeaturedCollections() {
        val listOfFeaturedCollections = networkRepositoryInterface
            .getFeaturedCollections(
                page = START_PAGE,
                perPage = QUANTITY_OF_COLLECTIONS
            )
        stateOfFeaturedCollection.emit(listOfFeaturedCollections)
    }
}