package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

data class GenreWithTvShows(
    @Embedded
    val genre: GenreEntity,

    @Relation(
        parentColumn = "genre_id",
        entityColumn = "tv_show_id",
        associateBy = Junction(TvShowGenreCrossRef::class))
    val tvShows: List<TvShowEntity>
)