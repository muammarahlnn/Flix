package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.*
import io.reactivex.Flowable

interface IRemoteDataSource {
    // movie
    fun getMovieDetail(movieId: Int): Flowable<ApiResponse<MovieDetailResponse>>
    fun getNowPlayingMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>>
    fun getUpcomingMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>>
    fun getPopularMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>>
    fun getTopRatedMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>>
    fun getMovieCredits(movieId: Int): Flowable<ApiResponse<List<CastResponse>>>

    // tv show
    fun getTvShowDetail(tvShowId: Int): Flowable<ApiResponse<TvShowDetailResponse>>
    fun getAiringTodayTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>>
    fun getOnTheAirTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>>
    fun getPopularTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>>
    fun getTopRatedTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>>
    fun getTvShowCredits(tvShowId: Int): Flowable<ApiResponse<List<CastResponse>>>

    // person
    fun getPersonDetail(personId: Int): Flowable<ApiResponse<PersonResponse>>
}