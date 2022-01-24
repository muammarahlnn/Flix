package com.ardnn.flix.data.source.local.entity

import com.ardnn.flix.data.source.remote.Const
import com.ardnn.flix.data.source.remote.ImageSize

data class MovieEntity(
    val id: Int,
    val title: String?,
    val releaseDate: String?,
    val posterUrl: String?,
    val rating: Float?
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }
}