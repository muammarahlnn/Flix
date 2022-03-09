package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.SectionMovieEntity

data class SectionWithMovies(
    @Embedded
    val section: SectionMovieEntity,

    @Relation(
        parentColumn = "section_id",
        entityColumn = "movie_id",
        associateBy = Junction(SectionMovieCrossRef::class)
    )
    val movies: List<MovieEntity>
)