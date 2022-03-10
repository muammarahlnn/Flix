package com.ardnn.flix.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.local.entity.relation.*
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.CastResponse
import com.ardnn.flix.vo.Resource

interface IFlixRepository {
    // === section ===================================================================
    fun getSectionWithMovies(page: Int, section: Int, filter: String): LiveData<Resource<List<MovieEntity>>>

    fun getSectionWithTvShows(page: Int, section: Int, filter: String): LiveData<Resource<List<TvShowEntity>>>

    // === movie ===================================================================
    fun getMovieWithGenres(movieId: Int): LiveData<Resource<MovieWithGenres>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    // not resource yet
    fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteMovie(movie: MovieEntity, state: Boolean)

    // === tv show ===================================================================
    fun getTvShowWithGenres(tvShowId: Int): LiveData<Resource<TvShowWithGenres>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>

    // not resource yet
    fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>>

    fun setIsFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)

    // === genre ===================================================================
    fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovies>

    fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShows>
}