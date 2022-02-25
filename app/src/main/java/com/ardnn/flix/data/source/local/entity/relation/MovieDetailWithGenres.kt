package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity

data class MovieDetailWithGenres(
    @Embedded
    val movieDetail: MovieDetailEntity,

    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        associateBy = Junction(MovieDetailGenreCrossRef::class))
    val genres: List<GenreEntity>
)