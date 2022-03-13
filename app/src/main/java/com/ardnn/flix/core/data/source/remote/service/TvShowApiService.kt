package com.ardnn.flix.core.data.source.remote.service

import com.ardnn.flix.core.data.source.remote.response.CreditsResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApiService {
    @GET("3/tv/{tv_id}")
    fun getTvShowDetails(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Flowable<TvShowDetailResponse>

    @GET("3/tv/{tv_id}/credits")
    fun getTvShowCredits(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Flowable<CreditsResponse>

    @GET("3/tv/airing_today")
    fun getAiringTodayTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<TvShowsResponse>

    @GET("3/tv/on_the_air")
    fun getOnTheAirTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<TvShowsResponse>

    @GET("3/tv/popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<TvShowsResponse>

    @GET("3/tv/top_rated")
    fun getTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<TvShowsResponse>
}