package com.daniil.halushka.pexelsapp.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daniil.halushka.pexelsapp.domain.models.DomainPhoto
import com.daniil.halushka.pexelsapp.domain.models.DomainPhotoSrc
import java.io.Serializable

@Entity("bookmark_photo")
data class Photo(
    @PrimaryKey
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    @Embedded(prefix = "src_")
    val src: PhotoSrc,
    val liked: Boolean,
    val alt: String
) : Serializable

data class PhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)

fun Photo.toDomainPhoto(): DomainPhoto {
    val (id, width, height, url, photographer, photographerUrl, photographerId, avgColor, src, liked, alt) = this
    val (original, large2x, large, medium, small, portrait, landscape, tiny) = src

    return DomainPhoto(
        id = id,
        width = width,
        height = height,
        url = url,
        photographer = photographer,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        src = DomainPhotoSrc(
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