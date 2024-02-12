package com.daniil.halushka.pexelsapp.data.repository

import com.daniil.halushka.pexelsapp.data.database.BookmarksDatabase
import com.daniil.halushka.pexelsapp.data.models.Photo
import com.daniil.halushka.pexelsapp.data.models.toDomainPhoto
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.repository.DatabaseRepositoryInterface

class DatabaseRepository(
    private val database: BookmarksDatabase
) : DatabaseRepositoryInterface {
    override suspend fun getPhotoById(id: Int): DomainPhoto? {
        return runCatching {
            database.dao.getPhotoById(id).firstOrNull()?.toDomainPhoto()
        }.getOrNull()
    }

    override suspend fun getBookmarksList(): List<DomainPhoto> {
        return runCatching {
            database.dao.getAllBookmarks().map {
                it.toDomainPhoto()
            }
        }.getOrElse { emptyList() }
    }

    override suspend fun addBookmarksPhoto(photo: Photo) {
        runCatching {
            database.dao.addBookmarkPhoto(photo)
        }
    }

    override suspend fun deleteBookmarkPhoto(photo: Photo) {
        database.dao.removeBookmarkPhoto(photo)
    }

    override suspend fun countPhotosById(id: Int): Int {
        return runCatching {
            database.dao.countById(id)
        }.getOrElse { 0 }
    }
}