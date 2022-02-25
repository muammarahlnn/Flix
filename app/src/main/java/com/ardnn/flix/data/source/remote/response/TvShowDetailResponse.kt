package com.ardnn.flix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val title: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("first_air_date")
    val firstAirDate: String?,

    @field:SerializedName("last_air_date")
    val lastAirDate: String?,

    @field:SerializedName("episode_run_time")
    val runtimes: List<Int>?,

    @field:SerializedName("vote_average")
    val rating: Float?,

    @field:SerializedName("poster_path")
    val posterUrl: String?,

    @field:SerializedName("backdrop_path")
    val wallpaperUrl: String?,

    @field:SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,

    @field:SerializedName("genres")
    val genreList: List<GenreResponse>?,
)