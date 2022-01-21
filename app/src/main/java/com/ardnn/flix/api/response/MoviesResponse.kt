package com.ardnn.flix.api.response

import com.ardnn.flix.api.Const
import com.ardnn.flix.api.ImageSize
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @field:SerializedName("results")
    val movies: List<Movie>
)

data class Movie(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("poster_path")
    val posterUrl: String?,

    @field:SerializedName("vote_average")
    val rating: Float?,
) {
    fun getPosterUrl(size: ImageSize): String {
        return "${Const.IMG_URL}${size.getValue()}$posterUrl"
    }
}