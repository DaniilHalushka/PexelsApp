package com.daniil.halushka.pexelsapp.data.models

import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection

data class ResponseFeaturedCollections(
    val collections: List<FeaturedCollection>,
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val nextPage: String,
    val prevPage: String
)

data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)

fun List<FeaturedCollection>.toDomainFeaturedCollections(): List<DomainFeaturedCollection> {
    return map { (id, title, description, isPrivate, mediaCount, photosCount, videosCount) ->
        DomainFeaturedCollection(
            id = id,
            title = title,
            description = description,
            private = isPrivate,
            mediaCount = mediaCount,
            photosCount = photosCount,
            videosCount = videosCount
        )
    }
}
