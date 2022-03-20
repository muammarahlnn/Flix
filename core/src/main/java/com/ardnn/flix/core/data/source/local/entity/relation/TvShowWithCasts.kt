package com.ardnn.flix.core.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.ardnn.flix.core.data.source.local.entity.CastEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity

data class TvShowWithCasts(
    @Embedded
    val tvShow: TvShowEntity,

    @Relation(
        parentColumn = "tv_show_id",
        entityColumn = "film_id")
    val casts: List<CastEntity>
)