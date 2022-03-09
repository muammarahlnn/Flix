package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "section_tv_show_detail_entities",
    primaryKeys = ["section_id", "tv_show_id"]
)
data class SectionTvShowCrossRef(
    @ColumnInfo(name = "section_id")
    val sectionId: Int,

    @ColumnInfo(name = "tv_show_id", index = true)
    val tvShowId: Int
)