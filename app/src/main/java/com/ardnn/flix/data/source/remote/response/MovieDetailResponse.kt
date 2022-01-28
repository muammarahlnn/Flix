package com.ardnn.flix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("original_title")
    val title: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("runtime")
    val runtime: Int?,

    @field:SerializedName("vote_average")
    val rating: Float?,

    @field:SerializedName("poster_path")
    val posterUrl: String?,

    @field:SerializedName("backdrop_path")
    val wallpaperUrl: String?,

    @field:SerializedName("genres")
    val genreList: List<GenreResponse>?
)