package com.ardnn.flix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @field:SerializedName("results")
    val tvShows: List<TvShowResponse>
)

data class TvShowResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val title: String?,

    @field:SerializedName("first_air_date")
    val firstAirDate: String?,

    @field:SerializedName("poster_path")
    val posterUrl: String?,

    @field:SerializedName("vote_average")
    val rating: Float?,
)