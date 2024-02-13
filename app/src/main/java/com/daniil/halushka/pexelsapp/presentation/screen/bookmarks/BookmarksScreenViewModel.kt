package com.daniil.halushka.pexelsapp.presentation.screen.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.repository.DatabaseRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenViewModel @Inject constructor(
    private val databaseRepositoryInterface: DatabaseRepositoryInterface
) : ViewModel() {
    val bookmarkPhotos = MutableStateFlow(emptyList<DomainPhoto>())
    val actualError = MutableStateFlow(false)

    init {
        loadBookmarkPhotos()
    }

    fun loadBookmarkPhotos() {
        viewModelScope.launch {
            val bookmarks = databaseRepositoryInterface.getBookmarksList()
            bookmarkPhotos.value = bookmarks
            actualError.value = bookmarks.isEmpty()
        }
    }
}