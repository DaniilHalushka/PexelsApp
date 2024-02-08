package com.daniil.halushka.pexelsapp.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.repository.NetworkRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepositoryInterface
) : ViewModel() {
    val choosePhoto = MutableStateFlow<DomainPhoto?>(null)

    fun getHomeScreenPhoto(id: Int?) {
        if (id != null) {
            fetchPhotoById(id)
        }
    }

    private fun fetchPhotoById(id: Int) {
        viewModelScope.launch {
            val photo = networkRepository.getPhotoById(id)
            choosePhoto.emit(photo)
        }
    }
}
