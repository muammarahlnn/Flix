package com.ardnn.flix.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ardnn.flix.core.data.source.local.entity.*
import com.ardnn.flix.core.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionTvShowCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.core.data.source.local.room.dao.*

@Database(
    entities = [
        GenreEntity::class,
        MovieEntity::class,
        TvShowEntity::class,
        CastEntity::class,
        SectionMovieEntity::class,
        SectionTvShowEntity::class,
        MovieGenreCrossRef::class,
        TvShowGenreCrossRef::class,
        SectionMovieCrossRef::class,
        SectionTvShowCrossRef::class,
    ],
    version = 1,
    exportSchema = false)
abstract class FlixDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao

    abstract fun sectionDao(): SectionDao

    abstract fun genreDao(): GenreDao

    abstract fun castDao(): CastDao
}