package com.ardnn.flix.data.source.local.entity

import com.ardnn.flix.data.source.remote.Const
import com.ardnn.flix.data.source.remote.ImageSize

data class TvShowDetailEntity(
    val id: Int,
    val title: String?,
    val overview: String?,
    val firstAirDate: String?,
    val lastAirDate: String?,
    val runtimes: List<Int>?,
    val rating: Float?,
    val posterUrl: String?,
    val wallpaperUrl: String?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val genreList: List<GenreEntity>?,
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$wallpaperUrl"
    }
}