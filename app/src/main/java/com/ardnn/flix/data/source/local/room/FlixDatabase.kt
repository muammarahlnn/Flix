package com.ardnn.flix.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.data.source.local.entity.relation.SectionTvShowCrossRef
import com.ardnn.flix.data.source.local.entity.relation.TvShowGenreCrossRef

@Database(
    entities = [
        GenreEntity::class,
        MovieEntity::class,
        TvShowEntity::class,
        SectionMovieEntity::class,
        SectionTvShowEntity::class,
        MovieGenreCrossRef::class,
        TvShowGenreCrossRef::class,
        SectionMovieCrossRef::class,
        SectionTvShowCrossRef::class
    ],
    version = 1,
    exportSchema = false)
abstract class FlixDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun sectionDao(): SectionDao
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