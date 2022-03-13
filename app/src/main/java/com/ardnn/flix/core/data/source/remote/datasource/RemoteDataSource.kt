package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.*
import io.reactivex.Flowable

class RemoteDataSource private constructor(
    private val movieDataSource: MovieDataSource,
    private val tvShowDataSource: TvShowDataSource,
    private val personDataSource: PersonDataSource
) : IRemoteDataSource {

    // ===== movie ======================================================================
    override fun getMovieDetail(movieId: Int): Flowable<ApiResponse<MovieDetailResponse>> =
        movieDataSource.getDetail(movieId)

    override fun getNowPlayingMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getFirstSectionFilms(page)

    override fun getUpcomingMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getSecondSectionFilms(page)

    override fun getPopularMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getThirdSectionFilms(page)

    override fun getTopRatedMovies(page: Int): Flowable<ApiResponse<List<MovieResponse>>> =
        movieDataSource.getFourthSectionFilms(page)

    override fun getMovieCredits(movieId: Int): Flowable<ApiResponse<List<CastResponse>>> =
        movieDataSource.getFilmCredits(movieId)


    // ===== tv show ======================================================================
    override fun getTvShowDetail(tvShowId: Int): Flowable<ApiResponse<TvShowDetailResponse>> =
        tvShowDataSource.getDetail(tvShowId)

    override fun getAiringTodayTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getFirstSectionFilms(page)

    override fun getOnTheAirTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getSecondSectionFilms(page)

    override fun getPopularTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getThirdSectionFilms(page)

    override fun getTopRatedTvShows(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> =
        tvShowDataSource.getFourthSectionFilms(page)

    override fun getTvShowCredits(tvShowId: Int): Flowable<ApiResponse<List<CastResponse>>> =
        tvShowDataSource.getFilmCredits(tvShowId)


    // ===== person ======================================================================
    override fun getPersonDetail(personId: Int): Flowable<ApiResponse<PersonResponse>> =
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