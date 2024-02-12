package com.daniil.halushka.pexelsapp.data.database.di

import android.app.Application
import androidx.room.Room
import com.daniil.halushka.pexelsapp.data.database.BookmarksDatabase
import com.daniil.halushka.pexelsapp.data.repository.DatabaseRepository
import com.daniil.halushka.pexelsapp.domain.repository.DatabaseRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideBookmarksDatabase(
        application: Application
    ): BookmarksDatabase {
        return Room.databaseBuilder(
            application,
            BookmarksDatabase::class.java,
            "bookmarks.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(
        database: BookmarksDatabase
    ): DatabaseRepositoryInterface {
        return DatabaseRepository(database)
    }
}