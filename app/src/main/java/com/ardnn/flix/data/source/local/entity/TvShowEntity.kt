package com.ardnn.flix.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ardnn.flix.data.source.remote.Const
import com.ardnn.flix.data.source.remote.ImageSize

@Entity(tableName = "tv_show_entities")
data class TvShowEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val pId: Int,

    @ColumnInfo(name = "tv_show_id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String?,

    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,

    @ColumnInfo(name = "rating")
    val rating: Float?
) {
    @ColumnInfo(name = "section")
    var section: Int = -1

    fun getPosterUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }
}