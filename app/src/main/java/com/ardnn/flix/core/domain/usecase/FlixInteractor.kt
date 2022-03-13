package com.ardnn.flix.core.domain.usecase

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.repository.FlixRepository
import com.ardnn.flix.core.vo.Resource
import io.reactivex.Flowable

class FlixInteractor(
    private val flixRepository: FlixRepository
) : FlixUseCase{

    override fun getSectionWithMovies(
        page: Int,
        section: Int,
        filter: String
    ): Flowable<Resource<List<Movie>>> =
        flixRepository.getSectionWithMovies(page, section, filter)

    override fun getSectionWithTvShows(
        page: Int,
        section: Int,
        filter: String
    ): Flowable<Resource<List<TvShow>>> =
        flixRepository.getSectionWithTvShows(page, section, filter)

    override fun getMovieWithGenres(movieId: Int): Flowable<Resource<Movie>> =
        flixRepository.getMovieWithGenres(movieId)

    override fun getFavoriteMovies(): Flowable<List<Movie>> =
        flixRepository.getFavoriteMovies()

    override fun getMovieCredits(movieId: Int): Flowable<ApiResponse<List<CastResponse>>> =
        flixRepository.getMovieCredits(movieId)

    override fun setIsFavoriteMovie(movie: Movie, state: Boolean) =
        flixRepository.setIsFavoriteMovie(movie, state)

    override fun getTvShowWithGenres(tvShowId: Int): Flowable<Resource<TvShow>> =
        flixRepository.getTvShowWithGenres(tvShowId)

    override fun getFavoriteTvShows(): Flowable<List<TvShow>> =
        flixRepository.getFavoriteTvShows()

    override fun getTvShowCredits(tvShowId: Int): Flowable<ApiResponse<List<CastResponse>>> =
        flixRepository.getTvShowCredits(tvShowId)

    override fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean) =
        flixRepository.setIsFavoriteTvShow(tvShow, state)

    override fun getGenreWithMovies(genreId: Int): Flowable<Genre> =
        flixRepository.getGenreWithMovies(genreId)

    override fun getGenreWithTvShows(genreId: Int): Flowable<Genre> =
        flixRepository.getGenreWithTvShows(genreId)

}