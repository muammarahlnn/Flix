package com.ardnn.flix.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ardnn.flix.core.data.source.local.entity.*
import com.ardnn.flix.core.data.source.local.entity.relation.*
import com.ardnn.flix.core.data.source.local.room.GenreDao
import com.ardnn.flix.core.data.source.local.room.MovieDao
import com.ardnn.flix.core.data.source.local.room.SectionDao
import com.ardnn.flix.core.data.source.local.room.TvShowDao
import com.ardnn.flix.core.util.SortUtils

class LocalDataSource private constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val sectionDao: SectionDao,
    private val genreDao: GenreDao
) {

    // === movie dao ===================================================================
    fun getMovie(movieId: Int): MovieEntity =
        movieDao.getMovie(movieId)

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> =
        movieDao.getFavoriteMovies()

    fun getMovieWithGenres(movieId: Int): LiveData<MovieWithGenres> =
        movieDao.getMovieWithGenres(movieId)

    fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    fun insertMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef) {
        movieDao.insertMovieGenreCrossRef(crossRef)
    }

    fun updateMovie(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }

    fun setIsFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        updateMovie(movie)
    }

    // === tv show dao ===================================================================
    fun getTvShow(tvShowId: Int): TvShowEntity =
        tvShowDao.getTvShow(tvShowId)

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> =
        tvShowDao.getFavoriteTvShows()

    fun getTvShowWithGenres(tvShowId: Int): LiveData<TvShowWithGenres> =
        tvShowDao.getTvShowWithGenres(tvShowId)

    fun insertTvShows(tvShows: List<TvShowEntity>) {
        tvShowDao.insertTvShows(tvShows)
    }

    fun insertTvShow(tvShow: TvShowEntity) {
        tvShowDao.insertTvShow(tvShow)
    }

    fun insertTvShowGenreCrossRef(crossRef: TvShowGenreCrossRef) =
        tvShowDao.insertTvShowGenreCrossRef(crossRef)

    fun updateTvShow(tvShow: TvShowEntity) {
        tvShowDao.updateTvShow(tvShow)
    }

    fun setIsFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        updateTvShow(tvShow)
    }

    // === section dao ===================================================================
    fun getSectionWithMovies(section: Int, filter: String): LiveData<List<MovieEntity>> {
        val query = SortUtils.getSortedSection(SortUtils.MOVIES, section, filter)
        return sectionDao.getSectionWithMovies(query)
    }

    fun getSectionWithTvShows(section: Int, filter: String): LiveData<List<TvShowEntity>> {
        val query = SortUtils.getSortedSection(SortUtils.TV_SHOWS, section, filter)
        return sectionDao.getSectionWithTvShows(query)
    }

    fun insertSectionMovie(sectionMovie: SectionMovieEntity) {
        sectionDao.insertSectionMovie(sectionMovie)
    }

    fun insertSectionTvShow(sectionTvShow: SectionTvShowEntity) {
        sectionDao.insertSectionTvShow(sectionTvShow)
    }

    fun insertSectionMovieCrossRef(crossRef: SectionMovieCrossRef) {
        sectionDao.insertSectionMovieCrossRef(crossRef)
    }

    fun insertSectionTvShowCrossRef(crossRef: SectionTvShowCrossRef) {
        sectionDao.insertSectionTvShowCrossRef(crossRef)
    }

    // === genre dao ===================================================================
    fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovies> =
        genreDao.getGenreWithMovies(genreId)

    fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShows> =
        genreDao.getGenreWithTvShows(genreId)

    fun insertGenre(genres: List<GenreEntity>) {
        genreDao.insertGenre(genres)
    }

    fun updateGenre(genre: GenreEntity) {
        genreDao.updateGenre(genre)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(
            movieDao: MovieDao,
            tvShowDao: TvShowDao,
            sectionDao: SectionDao,
            genreDao: GenreDao
        ): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao, tvShowDao, sectionDao, genreDao)
    }
}