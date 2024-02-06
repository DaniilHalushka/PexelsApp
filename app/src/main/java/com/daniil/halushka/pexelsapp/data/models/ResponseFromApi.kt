package com.daniil.halushka.pexelsapp.data.models

data class ResponseFromApi(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val totalResults: Int,
    val nextPage: String?
)