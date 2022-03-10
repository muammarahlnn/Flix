package com.ardnn.flix.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ardnn.flix.core.data.source.remote.Const
import com.ardnn.flix.core.data.source.remote.ImageSize

@Entity(tableName = "tv_show_entities")
data class TvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tv_show_id")
    val id: Int,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "first_air_date")
    var firstAirDate: String? = null,

    @ColumnInfo(name = "last_air_date")
    var lastAirDate: String? = null,

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

    @ColumnInfo(name = "number_of_episodes")
    var numberOfEpisodes: Int? = null,

    @ColumnInfo(name = "number_of_seasons")
    var numberOfSeasons: Int? = null,
) {
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false

    @ColumnInfo(name = "is_detail_fetched")
    var isDetailFetched: Boolean = false

    fun getPosterUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$wallpaperUrl"
    }
}