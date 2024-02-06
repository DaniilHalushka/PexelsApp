package com.daniil.halushka.pexelsapp.data.repository

import com.daniil.halushka.pexelsapp.constants.Constants.API_KEY
import com.daniil.halushka.pexelsapp.data.models.toDomainFeaturedCollections
import com.daniil.halushka.pexelsapp.data.models.toDomainPhoto
import com.daniil.halushka.pexelsapp.data.network.api.PexelsApiService
import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.repository.NetworkRepositoryInterface

class NetworkRepository(
    private val apiService: PexelsApiService
) : NetworkRepositoryInterface {

    private suspend fun <T> executeRequest(
        request: suspend () -> T
    ): T? =
        try {
            request()
        } catch (exception: Exception) {
            null
        }

    override suspend fun getPhotoById(id: Int): DomainPhoto? =
        executeRequest {
            apiService.getPhotoById(API_KEY, id).toDomainPhoto()
        }

    override suspend fun getPhotosBySearch(
        query: String,
        page: Int,
        perPage: Int
    ): List<DomainPhoto> =
        executeRequest {
            apiService.getPhotosBySearch(API_KEY, query, page, perPage)
                .photos.let { photos ->
                    photos.takeIf { it.isNotEmpty() }?.map { it.toDomainPhoto() } ?: emptyList()
                }
        } ?: emptyList()

    override suspend fun getCuratedPhotos(
        page: Int,
        perPage: Int
    ): List<DomainPhoto> =
        executeRequest {
            apiService.getCuratedPhotos(API_KEY, page, perPage)
                .photos.let { photos ->
                    photos.takeIf { it.isNotEmpty() }?.map { it.toDomainPhoto() } ?: emptyList()
                }
        } ?: emptyList()

    override suspend fun getFeaturedCollections(
        page: Int,
        perPage: Int
    ): List<DomainFeaturedCollection> =
        executeRequest {
            apiService.getFeaturedCollections(API_KEY, page, perPage)
                .collections.toDomainFeaturedCollections()
        } ?: emptyList()
}
