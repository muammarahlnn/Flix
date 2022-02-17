package com.ardnn.flix.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.local.entity.relation.GenreWithMovieDetails
import com.ardnn.flix.data.source.local.entity.relation.GenreWithTvShowDetails
import com.ardnn.flix.data.source.local.entity.relation.MovieDetailWithGenres
import com.ardnn.flix.data.source.local.entity.relation.TvShowDetailWithGenres
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.CastResponse
import com.ardnn.flix.vo.Resource

interface FlixDataSource {
    fun getMovies(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetailWithGenres(movieId: Int): LiveData<Resource<MovieDetailWithGenres>>

    // not resource yet
    fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>>

    fun getTvShows(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetailWithGenres(tvShowId: Int): LiveData<Resource<TvShowDetailWithGenres>>

    // not resource yet
    fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieDetailEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowDetailEntity>>

    fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovieDetails>

    fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShowDetails>

    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, state: Boolean)

    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, state: Boolean)
}