package com.daniil.halushka.pexelsapp.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniil.halushka.pexelsapp.data.models.Photo
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.models.toPhoto
import com.daniil.halushka.pexelsapp.domain.repository.DatabaseRepositoryInterface
import com.daniil.halushka.pexelsapp.domain.repository.NetworkRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepositoryInterface,
    private val databaseRepository: DatabaseRepositoryInterface
) : ViewModel() {

    val choosePhoto = MutableStateFlow<DomainPhoto?>(null)
    val addPhotoToBookmarks = MutableStateFlow(false)

    fun getHomeScreenPhoto(id: Int?) {
        if (id != null) {
            fetchPhotoById(id)
        }
    }

    private fun fetchPhotoById(id: Int) {
        viewModelScope.launch {
            val photo = networkRepository.getPhotoById(id)
            choosePhoto.emit(photo)
            countPhotosById(id)
        }
    }

    fun getPhotoFromBookmarksScreen(id: Int?) {
        if (id != null) {
            fetchBookmarkById(id)
        }
    }

    private fun fetchBookmarkById(id: Int) {
        viewModelScope.launch {
            val photo = databaseRepository.getPhotoById(id)
            choosePhoto.emit(photo)
            countPhotosById(id)
        }
    }

    fun changeStateOnBookmarksButton(domainPhoto: DomainPhoto) {
        val photo = domainPhoto.toPhoto()
        viewModelScope.launch {
            toggleBookmark(photo, addPhotoToBookmarks.value)
            addPhotoToBookmarks.value = !addPhotoToBookmarks.value
        }
    }

    private suspend fun toggleBookmark(photo: Photo, addToBookmarks: Boolean) {
        if (addToBookmarks) {
            databaseRepository.deleteBookmarkPhoto(photo)
        } else {
            databaseRepository.addBookmarksPhoto(photo)
        }
    }

    private suspend fun countPhotosById(id: Int) {
        viewModelScope.launch {
            val count = databaseRepository.countPhotosById(id)
            addPhotoToBookmarks.value = count != 0
        }
    }
}
