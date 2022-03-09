package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

data class TvShowWithGenres (
    @Embedded
    val tvShow: TvShowEntity,

    @Relation(
        parentColumn = "tv_show_id",
        entityColumn = "genre_id",
        associateBy = Junction(TvShowGenreCrossRef::class))
    val genres: List<GenreEntity>
)
