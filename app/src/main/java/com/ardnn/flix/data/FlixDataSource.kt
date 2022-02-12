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
import com.ardnn.flix.vo.Resource

interface FlixDataSource {
    fun getMovies(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetailWithGenres(movieId: Int): LiveData<Resource<MovieDetailWithGenres>>

    fun getTvShows(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetailWithGenres(tvShowId: Int): LiveData<Resource<TvShowDetailWithGenres>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieDetailEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowDetailEntity>>

    fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovieDetails>

    fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShowDetails>

    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, state: Boolean)

    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, state: Boolean)
}