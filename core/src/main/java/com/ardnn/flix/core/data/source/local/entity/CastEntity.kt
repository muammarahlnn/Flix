package com.ardnn.flix.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast_entities")
data class CastEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cast_id")
    val id: Int,

    @ColumnInfo(name = "film_id")
    val filmId: Int,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "character")
    var character: String = "",

    @ColumnInfo(name = "profile_url")
    var profileUrl: String = "",
)