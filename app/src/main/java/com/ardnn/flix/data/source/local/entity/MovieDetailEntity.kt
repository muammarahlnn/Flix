package com.ardnn.flix.data.source.local.entity

import com.ardnn.flix.data.source.remote.Const
import com.ardnn.flix.data.source.remote.ImageSize

data class MovieDetailEntity(
    val id: Int,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val rating: Float?,
    val posterUrl: String?,
    val wallpaperUrl: String?,
    val genreList: List<GenreEntity>?,
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }

    fun getWallpaperUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$wallpaperUrl"
    }
}