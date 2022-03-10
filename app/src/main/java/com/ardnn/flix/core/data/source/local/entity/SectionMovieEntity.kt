package com.ardnn.flix.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "section_movie_entities")
data class SectionMovieEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "section_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
)