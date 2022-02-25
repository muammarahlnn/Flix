package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity

data class GenreWithTvShowDetails(
    @Embedded
    val genre: GenreEntity,

    @Relation(
        parentColumn = "genre_id",
        entityColumn = "tv_show_id",
        associateBy = Junction(TvShowDetailGenreCrossRef::class))
    val tvShowDetails: List<TvShowDetailEntity>
)