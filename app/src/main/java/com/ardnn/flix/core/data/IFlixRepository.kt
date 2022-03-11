package com.ardnn.flix.core.data

import androidx.lifecycle.LiveData
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.vo.Resource

interface IFlixRepository {
    // === section ===================================================================
    fun getSectionWithMovies(page: Int, section: Int, filter: String): LiveData<Resource<List<Movie>>>

    fun getSectionWithTvShows(page: Int, section: Int, filter: String): LiveData<Resource<List<TvShow>>>

    // === movie ===================================================================
    fun getMovieWithGenres(movieId: Int): LiveData<Resource<Movie>>

    fun getFavoriteMovies(): LiveData<List<Movie>>

    // not resource yet
    fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteMovie(movie: Movie, state: Boolean)

    // === tv show ===================================================================
    fun getTvShowWithGenres(tvShowId: Int): LiveData<Resource<TvShow>>

    fun getFavoriteTvShows(): LiveData<List<TvShow>>

    // not resource yet
    fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean)

    // === genre ===================================================================
    fun getGenreWithMovies(genreId: Int): LiveData<Genre>

    fun getGenreWithTvShows(genreId: Int): LiveData<Genre>
}