package com.ardnn.flix.core.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.ardnn.flix.core.data.source.local.entity.CastEntity
import com.ardnn.flix.core.data.source.local.entity.MovieEntity

data class MovieWithCasts(
    @Embedded
    val movie: MovieEntity,

    @Relation(
        parentColumn = "movie_id",
        entityColumn = "film_id")
    val casts: List<CastEntity>
)