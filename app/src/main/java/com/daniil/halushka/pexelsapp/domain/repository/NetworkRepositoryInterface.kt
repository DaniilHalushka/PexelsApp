package com.daniil.halushka.pexelsapp.domain.repository

import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto

interface NetworkRepositoryInterface {
    suspend fun getPhotoById(
        id: Int
    ): DomainPhoto?

    suspend fun getPhotosBySearch(
        query: String,
        page: Int,
        perPage: Int,
    ): List<DomainPhoto>

    suspend fun getCuratedPhotos(
        page: Int,
        perPage: Int,
    ): List<DomainPhoto>

    suspend fun getFeaturedCollections(
        page: Int,
        perPage: Int,
    ): List<DomainFeaturedCollection>
}