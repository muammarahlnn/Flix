package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "section_movie_cross_ref",
    primaryKeys = ["section_id", "movie_id"]
)
data class SectionMovieCrossRef(
    @ColumnInfo(name = "section_id")
    val sectionId: Int,

    @ColumnInfo(name = "movie_id", index = true)
    val movieId: Int
)