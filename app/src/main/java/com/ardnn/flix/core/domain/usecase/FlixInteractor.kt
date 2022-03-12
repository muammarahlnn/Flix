package com.ardnn.flix.core.domain.usecase

import androidx.lifecycle.LiveData
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.repository.FlixRepository
import com.ardnn.flix.core.vo.Resource

class FlixInteractor(
    private val flixRepository: FlixRepository
) : FlixUseCase{

    override fun getSectionWithMovies(
        page: Int,
        section: Int,
        filter: String
    ): LiveData<Resource<List<Movie>>> =
        flixRepository.getSectionWithMovies(page, section, filter)

    override fun getSectionWithTvShows(
        page: Int,
        section: Int,
        filter: String
    ): LiveData<Resource<List<TvShow>>> =
        flixRepository.getSectionWithTvShows(page, section, filter)

    override fun getMovieWithGenres(movieId: Int): LiveData<Resource<Movie>> =
        flixRepository.getMovieWithGenres(movieId)

    override fun getFavoriteMovies(): LiveData<List<Movie>> =
        flixRepository.getFavoriteMovies()

    override fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>> =
        flixRepository.getMovieCredits(movieId)

    override fun setIsFavoriteMovie(movie: Movie, state: Boolean) =
        flixRepository.setIsFavoriteMovie(movie, state)

    override fun getTvShowWithGenres(tvShowId: Int): LiveData<Resource<TvShow>> =
        flixRepository.getTvShowWithGenres(tvShowId)

    override fun getFavoriteTvShows(): LiveData<List<TvShow>> =
        flixRepository.getFavoriteTvShows()

    override fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>> =
        flixRepository.getTvShowCredits(tvShowId)

    override fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean) =
        flixRepository.setIsFavoriteTvShow(tvShow, state)

    override fun getGenreWithMovies(genreId: Int): LiveData<Genre> =
        flixRepository.getGenreWithMovies(genreId)

    override fun getGenreWithTvShows(genreId: Int): LiveData<Genre> =
        flixRepository.getGenreWithTvShows(genreId)

}