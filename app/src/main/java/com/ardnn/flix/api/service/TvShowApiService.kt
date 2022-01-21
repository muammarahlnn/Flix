package com.ardnn.flix.api.service

import com.ardnn.flix.api.response.TvShowDetailResponse
import com.ardnn.flix.api.response.TvShowsResponse
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

    @GET("airing_today")
    fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<TvShowsResponse>
}