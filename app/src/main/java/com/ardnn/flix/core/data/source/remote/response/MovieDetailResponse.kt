package com.ardnn.flix.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @field:SerializedName("id")
    val id: Int = -1,

    @field:SerializedName("original_title")
    val title: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("runtime")
    val runtime: Int? = null,

    @field:SerializedName("vote_average")
    val rating: Float? = null,

    @field:SerializedName("poster_path")
    val posterUrl: String? = null,

    @field:SerializedName("backdrop_path")
    val wallpaperUrl: String? = null,

    @field:SerializedName("genres")
    val genreList: List<GenreResponse> = listOf()
)