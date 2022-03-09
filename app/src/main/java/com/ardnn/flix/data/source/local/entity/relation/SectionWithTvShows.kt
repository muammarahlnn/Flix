package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ardnn.flix.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

data class SectionWithTvShows(
    @Embedded
    val section: SectionTvShowEntity,

    @Relation(
        parentColumn = "section_id",
        entityColumn = "tv_show_id",
        associateBy = Junction(SectionTvShowCrossRef::class)
    )
    val tvShows: List<TvShowEntity>
)