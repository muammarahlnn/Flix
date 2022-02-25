package com.ardnn.flix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @field:SerializedName("results")
    val movies: List<MovieResponse>
)

data class MovieResponse(
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
)