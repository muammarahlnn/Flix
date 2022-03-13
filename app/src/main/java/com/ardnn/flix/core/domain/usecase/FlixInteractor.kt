package com.ardnn.flix.core.domain.usecase

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.repository.FlixRepository
import com.ardnn.flix.core.data.Resource
import kotlinx.coroutines.flow.Flow

class FlixInteractor(
    private val flixRepository: FlixRepository
) : FlixUseCase{

    override fun getSectionWithMovies(
        page: Int,
        section: Int,
        filter: String
    ): Flow<Resource<List<Movie>>> =
        flixRepository.getSectionWithMovies(page, section, filter)

    override fun getSectionWithTvShows(
        page: Int,
        section: Int,
        filter: String
    ): Flow<Resource<List<TvShow>>> =
        flixRepository.getSectionWithTvShows(page, section, filter)

    override fun getMovieWithGenres(movieId: Int): Flow<Resource<Movie>> =
        flixRepository.getMovieWithGenres(movieId)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        flixRepository.getFavoriteMovies()

    override fun getMovieCredits(movieId: Int): Flow<ApiResponse<List<CastResponse>>> =
        flixRepository.getMovieCredits(movieId)

    override fun setIsFavoriteMovie(movie: Movie, state: Boolean) =
        flixRepository.setIsFavoriteMovie(movie, state)

    override fun getTvShowWithGenres(tvShowId: Int): Flow<Resource<TvShow>> =
        flixRepository.getTvShowWithGenres(tvShowId)

    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        flixRepository.getFavoriteTvShows()

    override fun getTvShowCredits(tvShowId: Int): Flow<ApiResponse<List<CastResponse>>> =
        flixRepository.getTvShowCredits(tvShowId)

    override fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean) =
        flixRepository.setIsFavoriteTvShow(tvShow, state)

    override fun getGenreWithMovies(genreId: Int): Flow<Genre> =
        flixRepository.getGenreWithMovies(genreId)

    override fun getGenreWithTvShows(genreId: Int): Flow<Genre> =
        flixRepository.getGenreWithTvShows(genreId)

}