package com.ardnn.flix.data.source.remote.datasource

import androidx.lifecycle.LiveData
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.*

interface IRemoteDataSource {
    // movie
    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>>
    fun getNowPlayingMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>>
    fun getUpcomingMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>>
    fun getPopularMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>>
    fun getTopRatedMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>>
    fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>>

    // tv show
    fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvShowDetailResponse>>
    fun getAiringTodayTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>>
    fun getOnTheAirTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>>
    fun getPopularTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>>
    fun getTopRatedTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>>
    fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>>

    // person
    fun getPersonDetail(personId: Int): LiveData<ApiResponse<PersonResponse>>
}