package com.ardnn.flix.core.data.source.remote.service

import com.ardnn.flix.core.data.source.remote.response.CreditsResponse
import com.ardnn.flix.core.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.core.data.source.remote.response.MoviesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Flowable<MovieDetailResponse>

    @GET("3/movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Flowable<CreditsResponse>

    @GET("3/movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<MoviesResponse>

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<MoviesResponse>

    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<MoviesResponse>

    @GET("3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Flowable<MoviesResponse>
}