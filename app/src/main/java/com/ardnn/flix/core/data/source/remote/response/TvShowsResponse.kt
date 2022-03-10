package com.ardnn.flix.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @field:SerializedName("results")
    val tvShows: List<TvShowResponse> = listOf()
)

data class TvShowResponse(
    @field:SerializedName("id")
    val id: Int = -1,

    @field:SerializedName("name")
    val title: String? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("poster_path")
    val posterUrl: String? = null,

    @field:SerializedName("vote_average")
    val rating: Float? = null,

    @field:SerializedName("popularity")
    val popularity: Float? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>? = null
)