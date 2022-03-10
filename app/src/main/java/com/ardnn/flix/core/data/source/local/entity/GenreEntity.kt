package com.ardnn.flix.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_entities")
data class GenreEntity(

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "genre_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String? = null
)