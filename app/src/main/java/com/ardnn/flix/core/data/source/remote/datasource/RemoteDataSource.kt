package com.ardnn.flix.core.data.source.remote.datasource

import androidx.lifecycle.LiveData
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.*

class RemoteDataSource private constructor(
    private val movieDataSource: MovieDataSource,
    private val tvShowDataSource: TvShowDataSource,
    private val personDataSource: PersonDataSource
) : IRemoteDataSource {

    // ===== movie ======================================================================
    override fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>> =
        movieDataSource.getDetail(movieId)

    override fun getNowPlayingMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getFirstSectionFilms(page)

    override fun getUpcomingMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getSecondSectionFilms(page)

    override fun getPopularMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getThirdSectionFilms(page)

    override fun getTopRatedMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getFourthSectionFilms(page)

    override fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>> =
        movieDataSource.getFilmCredits(movieId)


    // ===== tv show ======================================================================
    override fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvShowDetailResponse>> =
        tvShowDataSource.getDetail(tvShowId)

    override fun getAiringTodayTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getFirstSectionFilms(page)

    override fun getOnTheAirTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getSecondSectionFilms(page)

    override fun getPopularTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getThirdSectionFilms(page)

    override fun getTopRatedTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getFourthSectionFilms(page)

    override fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>> =
        tvShowDataSource.getFilmCredits(tvShowId)


    // ===== person ======================================================================
    override fun getPersonDetail(personId: Int): LiveData<ApiResponse<PersonResponse>> =
        personDataSource.getDetail(personId)


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(
            movieData: MovieDataSource,
            tvShowData: TvShowDataSource,
            personData: PersonDataSource
        ): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(movieData, tvShowData, personData).apply {
                    instance = this
                }
            }
    }
}