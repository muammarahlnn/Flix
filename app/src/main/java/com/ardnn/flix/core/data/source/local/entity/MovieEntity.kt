package com.ardnn.flix.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ardnn.flix.core.data.source.remote.Const
import com.ardnn.flix.core.data.source.remote.ImageSizeTMDB

@Entity(tableName = "movie_entities")
data class MovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movie_id")
    val id: Int,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "runtime")
    var runtime: Int? = null,

    @ColumnInfo(name = "rating")
    var rating: Float? = null,

    @ColumnInfo(name = "popularity")
    var popularity: Float? = null,

    @ColumnInfo(name = "poster_url")
    var posterUrl: String? = null,

    @ColumnInfo(name = "wallpaper_url")
    var wallpaperUrl: String? = null,
) {
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false

    @ColumnInfo(name = "is_detail_fetched")
    var isDetailFetched: Boolean = false

    fun getPosterUrl(size: ImageSizeTMDB): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }

    fun getWallpaperUrl(size: ImageSizeTMDB): String {
        return "${Const.IMG_URL}${size.getValue()}$wallpaperUrl"
    }
}