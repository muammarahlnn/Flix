package com.ardnn.flix.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.vo.Resource

interface FlixDataSource {
    fun getMovies(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetailEntity>>

    fun getTvShows(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowDetailEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieDetailEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowDetailEntity>>

    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, state: Boolean)

    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, state: Boolean)
}