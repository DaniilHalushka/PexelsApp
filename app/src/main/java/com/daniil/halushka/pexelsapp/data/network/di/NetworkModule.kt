package com.daniil.halushka.pexelsapp.data.network.di

import com.daniil.halushka.pexelsapp.constants.Constants.BASE_URL
import com.daniil.halushka.pexelsapp.data.network.api.PexelsApiService
import com.daniil.halushka.pexelsapp.data.repository.NetworkRepository
import com.daniil.halushka.pexelsapp.domain.repository.NetworkRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(): PexelsApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(pexelsApiService: PexelsApiService): NetworkRepositoryInterface {
        return NetworkRepository(pexelsApiService)
    }


}