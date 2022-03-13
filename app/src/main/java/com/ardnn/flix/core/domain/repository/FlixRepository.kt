package com.ardnn.flix.core.domain.repository

import androidx.lifecycle.LiveData
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.vo.Resource
import io.reactivex.Flowable

interface FlixRepository {
    // === section ===================================================================
    fun getSectionWithMovies(page: Int, section: Int, filter: String): Flowable<Resource<List<Movie>>>

    fun getSectionWithTvShows(page: Int, section: Int, filter: String): Flowable<Resource<List<TvShow>>>

    // === movie ===================================================================
    fun getMovieWithGenres(movieId: Int): Flowable<Resource<Movie>>

    fun getFavoriteMovies(): Flowable<List<Movie>>

    // not resource yet
    fun getMovieCredits(movieId: Int): Flowable<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteMovie(movie: Movie, state: Boolean)

    // === tv show ===================================================================
    fun getTvShowWithGenres(tvShowId: Int): Flowable<Resource<TvShow>>

    fun getFavoriteTvShows(): Flowable<List<TvShow>>

    // not resource yet
    fun getTvShowCredits(tvShowId: Int): Flowable<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean)

    // === genre ===================================================================
    fun getGenreWithMovies(genreId: Int): Flowable<Genre>

    fun getGenreWithTvShows(genreId: Int): Flowable<Genre>
}