package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity

data class TvShowDetailWithGenres (
    @Embedded
    val tvShowDetail: TvShowDetailEntity,

    @Relation(
        parentColumn = "tv_show_id",
        entityColumn = "genre_id",
        associateBy = Junction(TvShowDetailGenreCrossRef::class))
    val genres: List<GenreEntity>
)
