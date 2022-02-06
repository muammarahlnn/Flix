package com.ardnn.flix.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailEntity::class,
        TvShowEntity::class,
        TvShowDetailEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class FlixDatabase : RoomDatabase() {
    abstract fun flixDao(): FlixDao

    companion object {
        @Volatile
        private var INSTANCE: FlixDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FlixDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FlixDatabase::class.java,
                    "Flix.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}