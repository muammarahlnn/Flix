package com.ardnn.flix.data.source.local.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "tv_show_genre_cross_ref",
    primaryKeys = ["tv_show_id", "genre_id"]
)
data class TvShowGenreCrossRef(
    @ColumnInfo(name = "tv_show_id")
    val tvShowId: Int,

    @ColumnInfo(name = "genre_id", index = true)
    val genreId: Int
)