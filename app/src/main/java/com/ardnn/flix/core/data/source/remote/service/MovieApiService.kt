package com.ardnn.flix.core.data.source.remote.service

import com.ardnn.flix.core.data.source.remote.response.CreditsResponse
import com.ardnn.flix.core.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.core.data.source.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse

    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): CreditsResponse

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesResponse
}