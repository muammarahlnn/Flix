package com.ardnn.flix.core.domain.usecase

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface FlixUseCase {
    // === section ===================================================================
    fun getSectionWithMovies(page: Int, section: Int, filter: String): Flow<Resource<List<Movie>>>

    fun getSectionWithTvShows(page: Int, section: Int, filter: String): Flow<Resource<List<TvShow>>>

    // === movie ===================================================================
    fun getMovieWithGenres(movieId: Int): Flow<Resource<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    // not resource yet
    fun getMovieCredits(movieId: Int): Flow<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteMovie(movie: Movie, state: Boolean)

    // === tv show ===================================================================
    fun getTvShowWithGenres(tvShowId: Int): Flow<Resource<TvShow>>

    fun getFavoriteTvShows(): Flow<List<TvShow>>

    // not resource yet
    fun getTvShowCredits(tvShowId: Int): Flow<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean)

    // === genre ===================================================================
    fun getGenreWithMovies(genreId: Int): Flow<Genre>

    fun getGenreWithTvShows(genreId: Int): Flow<Genre>
}