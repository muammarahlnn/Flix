package com.ardnn.flix.core.data.source.remote.service

import com.ardnn.flix.core.data.source.remote.response.CreditsResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {
    @GET("3/tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): TvShowDetailResponse

    @GET("3/tv/{tv_id}/credits")
    suspend fun getTvShowCredits(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): CreditsResponse

    @GET("3/tv/airing_today")
    suspend fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): TvShowsResponse

    @GET("3/tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): TvShowsResponse

    @GET("3/tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): TvShowsResponse

    @GET("3/tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): TvShowsResponse
}