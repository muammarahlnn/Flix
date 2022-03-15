package com.ardnn.flix.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_entities")
data class TvShowEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tv_show_id")
    val id: Int,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "first_air_date")
    var firstAirDate: String = "",

    @ColumnInfo(name = "last_air_date")
    var lastAirDate: String = "",

    @ColumnInfo(name = "runtime")
    var runtime: Int = 0,

    @ColumnInfo(name = "rating")
    var rating: Float = 0f,

    @ColumnInfo(name = "popularity")
    var popularity: Float = 0f,

    @ColumnInfo(name = "poster_url")
    var posterUrl: String = "",

    @ColumnInfo(name = "wallpaper_url")
    var wallpaperUrl: String = "",

    @ColumnInfo(name = "number_of_episodes")
    var numberOfEpisodes: Int = 0,

    @ColumnInfo(name = "number_of_seasons")
    var numberOfSeasons: Int = 0,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "is_detail_fetched")
    var isDetailFetched: Boolean = false,
)