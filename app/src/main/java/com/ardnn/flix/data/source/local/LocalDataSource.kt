package com.ardnn.flix.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.local.entity.relation.*
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

    fun getMovieDetailWithGenres(id: Int): LiveData<MovieDetailWithGenres> =
        flixDao.getMovieDetailWithGenres(id)

    fun getTvShowDetailWithGenres(id: Int): LiveData<TvShowDetailWithGenres> =
        flixDao.getTvShowDetailWithGenres(id)

    fun getGenreWithMovies(id: Int): LiveData<GenreWithMovieDetails> =
        flixDao.getGenreWithMovies(id)

    fun getGenreWithTvShows(id: Int): LiveData<GenreWithTvShowDetails> =
        flixDao.getGenreWithTvShows(id)

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

    fun insertMovieDetailGenreCrossRef(crossRef: MovieDetailGenreCrossRef) =
        flixDao.insertMovieDetailGenreCrossRef(crossRef)

    fun insertTvShowDetailGenreCrossRef(crossRef: TvShowDetailGenreCrossRef) =
        flixDao.insertTvShowDetailGenreCrossRef(crossRef)

    fun insertGenre(genres: List<GenreEntity>) =
        flixDao.insertGenre(genres)

    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, newState: Boolean) {
        movieDetail.isFavorite = newState
        flixDao.updateMovieDetail(movieDetail)
    }

    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, newState: Boolean) {
        tvShowDetail.isFavorite = newState
        flixDao.updateTvShowDetail(tvShowDetail)
    }
}