package com.ardnn.flix.data.source.remote.service

import com.ardnn.flix.data.source.remote.response.CreditsResponse
import com.ardnn.flix.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.data.source.remote.response.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {
    @GET("{tv_id}")
    fun getTvShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowDetailResponse>

    @GET("{tv_id}/credits")
    fun getTvShowCredits(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<CreditsResponse>

    @GET("airing_today")
    fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowsResponse>

    @GET("on_the_air")
    fun getOnTheAirTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowsResponse>

    @GET("popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowsResponse>

    @GET("top_rated")
    fun getTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowsResponse>
}