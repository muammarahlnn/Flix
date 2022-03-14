package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val tvShowDataSource: TvShowDataSource,
    private val personDataSource: PersonDataSource
) : IRemoteDataSource {

    // ===== movie ======================================================================
    override fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> =
        movieDataSource.getDetail(movieId)

    override fun getNowPlayingMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getFirstSectionFilms(page)

    override fun getUpcomingMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getSecondSectionFilms(page)

    override fun getPopularMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getThirdSectionFilms(page)

    override fun getTopRatedMovies(page: Int): Flow<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getFourthSectionFilms(page)

    override fun getMovieCredits(movieId: Int): Flow<ApiResponse<List<CastResponse>>> =
        movieDataSource.getFilmCredits(movieId)


    // ===== tv show ======================================================================
    override fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvShowDetailResponse>> =
        tvShowDataSource.getDetail(tvShowId)

    override fun getAiringTodayTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getFirstSectionFilms(page)

    override fun getOnTheAirTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getSecondSectionFilms(page)

    override fun getPopularTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getThirdSectionFilms(page)

    override fun getTopRatedTvShows(page: Int): Flow<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getFourthSectionFilms(page)

    override fun getTvShowCredits(tvShowId: Int): Flow<ApiResponse<List<CastResponse>>> =
        tvShowDataSource.getFilmCredits(tvShowId)


    // ===== person ======================================================================
    override fun getPersonDetail(personId: Int): Flow<ApiResponse<PersonResponse>> =
        personDataSource.getDetail(personId)

}