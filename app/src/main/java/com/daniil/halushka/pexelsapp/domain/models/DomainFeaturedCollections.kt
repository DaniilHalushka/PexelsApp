package com.daniil.halushka.pexelsapp.domain.models

data class DomainFeaturedCollection(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)