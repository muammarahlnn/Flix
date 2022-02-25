package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity

data class GenreWithMovieDetails(
    @Embedded
    val genre: GenreEntity,

    @Relation(
        parentColumn = "genre_id",
        entityColumn = "movie_id",
        associateBy = Junction(MovieDetailGenreCrossRef::class))
    val movieDetails: List<MovieDetailEntity>
)