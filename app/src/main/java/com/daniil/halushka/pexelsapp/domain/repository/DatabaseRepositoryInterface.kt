package com.daniil.halushka.pexelsapp.domain.repository

import com.daniil.halushka.pexelsapp.data.models.Photo
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto

interface DatabaseRepositoryInterface {
    suspend fun getPhotoById(id: Int): DomainPhoto?

    suspend fun getBookmarksList(): List<DomainPhoto>

    suspend fun addBookmarksPhoto(photo: Photo)

    suspend fun deleteBookmarkPhoto(photo: Photo)

    suspend fun countPhotosById(id: Int): Int
}