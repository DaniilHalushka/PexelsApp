package com.daniil.halushka.pexelsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daniil.halushka.pexelsapp.data.database.di.Dao
import com.daniil.halushka.pexelsapp.data.models.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class BookmarksDatabase : RoomDatabase() {
    abstract val dao: Dao
}