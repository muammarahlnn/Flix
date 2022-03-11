package com.ardnn.flix.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(
    @field:SerializedName("id")
    val id: Int = -1,

    @field:SerializedName("name")
    val title: String? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("last_air_date")
    val lastAirDate: String? = null,

    @field:SerializedName("episode_run_time")
    val runtimes: List<Int>? = null,

    @field:SerializedName("vote_average")
    val rating: Float? = null,

    @field:SerializedName("poster_path")
    val posterUrl: String? = null,

    @field:SerializedName("backdrop_path")
    val wallpaperUrl: String? = null,

    @field:SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,

    @field:SerializedName("genres")
    val genreList: List<GenreResponse>? = null,
)