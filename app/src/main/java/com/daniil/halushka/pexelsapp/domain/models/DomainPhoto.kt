package com.daniil.halushka.pexelsapp.domain.models

import com.daniil.halushka.pexelsapp.data.models.Photo
import com.daniil.halushka.pexelsapp.data.models.PhotoSrc
import java.io.Serializable

data class DomainPhoto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    val src: DomainPhotoSrc,
    val liked: Boolean,
    val alt: String
) : Serializable

data class DomainPhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

fun DomainPhoto.toPhoto(): Photo {
    val (id, width, height, url, photographer, photographerUrl, photographerId, avgColor, src, liked, alt) = this
    val (original, large2x, large, medium, small, portrait, landscape, tiny) = src

    return Photo(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        src = PhotoSrc(
            original = original,
            large2x = large2x,
            large = large,
            medium = medium,
            small = small,
            portrait = portrait,
            landscape = landscape,
            tiny = tiny
        ),
        liked = liked,
        alt = alt
    )
}