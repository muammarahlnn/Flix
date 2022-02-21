package com.ardnn.flix.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.local.entity.relation.MovieDetailGenreCrossRef
import com.ardnn.flix.data.source.local.entity.relation.TvShowDetailGenreCrossRef

@Database(
    entities = [
        GenreEntity::class,
        MovieEntity::class,
        MovieDetailEntity::class,
        TvShowEntity::class,
        TvShowDetailEntity::class,
        MovieDetailGenreCrossRef::class,
        TvShowDetailGenreCrossRef::class
    ],
    version = 1,
    exportSchema = false)
abstract class FlixDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun genreDao(): GenreDao

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