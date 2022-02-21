package com.ardnn.flix.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.local.entity.relation.*
import com.ardnn.flix.data.source.local.room.GenreDao
import com.ardnn.flix.data.source.local.room.MovieDao
import com.ardnn.flix.data.source.local.room.TvShowDao
import com.ardnn.flix.utils.SortUtils

class LocalDataSource
private constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val genreDao: GenreDao) {

    // === movie dao ===================================================================
    fun getMovies(section: Int, filter: String): DataSource.Factory<Int, MovieEntity> {
        val query = SortUtils.getSortedQuery(SortUtils.MOVIES, section, filter)
        return movieDao.getMovies(query)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieDetailEntity> =
        movieDao.getFavoriteMovies()

    fun getMovieDetailWithGenres(id: Int): LiveData<MovieDetailWithGenres> =
        movieDao.getMovieDetailWithGenres(id)

    fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    fun insertMovieDetail(movieDetail: MovieDetailEntity) {
        movieDao.insertMovieDetail(movieDetail)
    }

    fun insertMovieDetailGenreCrossRef(crossRef: MovieDetailGenreCrossRef) {
        movieDao.insertMovieDetailGenreCrossRef(crossRef)
    }

    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, newState: Boolean) {
        movieDetail.isFavorite = newState
        movieDao.updateMovieDetail(movieDetail)
    }

    // === tv show dao ===================================================================
    fun getTvShows(section: Int, filter: String): DataSource.Factory<Int, TvShowEntity> {
        val query = SortUtils.getSortedQuery(SortUtils.TV_SHOWS, section, filter)
        return tvShowDao.getTvShows(query)
    }

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowDetailEntity> =
        tvShowDao.getFavoriteTvShows()

    fun getTvShowDetailWithGenres(id: Int): LiveData<TvShowDetailWithGenres> =
        tvShowDao.getTvShowDetailWithGenres(id)

    fun insertTvShows(tvShows: List<TvShowEntity>) {
        tvShowDao.insertTvShows(tvShows)
    }

    fun insertTvShowDetail(tvShowDetail: TvShowDetailEntity) {
        tvShowDao.insertTvShowDetail(tvShowDetail)
    }

    fun insertTvShowDetailGenreCrossRef(crossRef: TvShowDetailGenreCrossRef) =
        tvShowDao.insertTvShowDetailGenreCrossRef(crossRef)

    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, newState: Boolean) {
        tvShowDetail.isFavorite = newState
        tvShowDao.updateTvShowDetail(tvShowDetail)
    }

    // === genre dao ===================================================================
    fun getGenreWithMovies(id: Int): LiveData<GenreWithMovieDetails> =
        genreDao.getGenreWithMovies(id)

    fun getGenreWithTvShows(id: Int): LiveData<GenreWithTvShowDetails> =
        genreDao.getGenreWithTvShows(id)

    fun insertGenre(genres: List<GenreEntity>) {
        genreDao.insertGenre(genres)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(
            movieDao: MovieDao,
            tvShowDao: TvShowDao,
            genreDao: GenreDao
        ): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao, tvShowDao, genreDao)
    }
}