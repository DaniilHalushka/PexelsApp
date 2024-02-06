package com.daniil.halushka.pexelsapp.data.network.api

import com.daniil.halushka.pexelsapp.data.models.Photo
import com.daniil.halushka.pexelsapp.data.models.ResponseFeaturedCollections
import com.daniil.halushka.pexelsapp.data.models.ResponseFromApi
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApiService {
    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Header("Authorization") apiKey: String,
        @Path("id") id: Int
    ): Photo

    @GET("search")
    suspend fun getPhotosBySearch(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): ResponseFromApi

    @GET("curated")
    suspend fun getCuratedPhotos(
        @Header("Authorization") apiKey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): ResponseFromApi

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Header("Authorization") apiKey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): ResponseFeaturedCollections
}