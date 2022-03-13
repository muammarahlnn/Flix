package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    // movie
    fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>>
    fun getNowPlayingMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>>
    fun getUpcomingMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>>
    fun getPopularMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>>
    fun getTopRatedMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>>
    fun getMovieCredits(movieId: Int): Flow<ApiResponse<List<CastResponse>>>

    // tv show
    fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvShowDetailResponse>>
    fun getAiringTodayTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>>
    fun getOnTheAirTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>>
    fun getPopularTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>>
    fun getTopRatedTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>>
    fun getTvShowCredits(tvShowId: Int): Flow<ApiResponse<List<CastResponse>>>

    // person
    fun getPersonDetail(personId: Int): Flow<ApiResponse<PersonResponse>>
}