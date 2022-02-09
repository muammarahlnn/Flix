package com.ardnn.flix.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.local.room.FlixDao
import com.ardnn.flix.utils.SortUtils

class LocalDataSource private constructor(private val flixDao: FlixDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(flixDao: FlixDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(flixDao)
    }

    fun getMovies(section: Int, filter: String): DataSource.Factory<Int, MovieEntity> {
        val query = SortUtils.getSortedQuery(SortUtils.MOVIES, section, filter)
        return flixDao.getMovies(query)
    }

    fun getTvShows(section: Int, filter: String): DataSource.Factory<Int, TvShowEntity> {
        val query = SortUtils.getSortedQuery(SortUtils.TV_SHOWS, section, filter)
        return flixDao.getTvShows(query)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieDetailEntity> =
        flixDao.getFavoriteMovies()

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowDetailEntity> =
        flixDao.getFavoriteTvShows()

    fun getMovieDetail(id: Int): LiveData<MovieDetailEntity> =
        flixDao.getMovieDetail(id)

    fun getTvShowDetail(id: Int): LiveData<TvShowDetailEntity> =
        flixDao.getTvShowDetail(id)

    fun insertMovies(movies: List<MovieEntity>) {
        flixDao.insertMovies(movies)
    }

    fun insertTvShows(tvShows: List<TvShowEntity>) {
        flixDao.insertTvShows(tvShows)
    }

    fun insertMovieDetail(movieDetail: MovieDetailEntity) {
        flixDao.insertMovieDetail(movieDetail)
    }

    fun insertTvShowDetail(tvShowDetail: TvShowDetailEntity) {
        flixDao.insertTvShowDetail(tvShowDetail)
    }

    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, newState: Boolean) {
        movieDetail.isFavorite = newState
        flixDao.updateMovieDetail(movieDetail)
    }

    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, newState: Boolean) {
        tvShowDetail.isFavorite = newState
        flixDao.updateTvShowDetail(tvShowDetail)
    }
}