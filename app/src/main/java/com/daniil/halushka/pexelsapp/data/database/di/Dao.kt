package com.daniil.halushka.pexelsapp.data.database.di

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daniil.halushka.pexelsapp.data.models.Photo

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmarkPhoto(photo: Photo)

    @Query("SELECT * FROM bookmark_photo")
    suspend fun getAllBookmarks(): List<Photo>

    @Query("SELECT * FROM bookmark_photo WHERE id LIKE :id")
    suspend fun getPhotoById(id: Int): List<Photo>

    @Query("SELECT COUNT(*) FROM bookmark_photo WHERE id = :id")
    suspend fun countById(id: Int): Int

    @Delete
    suspend fun removeBookmarkPhoto(photo: Photo)
}