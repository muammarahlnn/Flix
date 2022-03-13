package com.ardnn.flix.core.data.source.local

import com.ardnn.flix.core.data.source.local.entity.*
import com.ardnn.flix.core.data.source.local.entity.relation.*
import com.ardnn.flix.core.data.source.local.room.GenreDao
import com.ardnn.flix.core.data.source.local.room.MovieDao
import com.ardnn.flix.core.data.source.local.room.SectionDao
import com.ardnn.flix.core.data.source.local.room.TvShowDao
import com.ardnn.flix.core.util.SortUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val sectionDao: SectionDao,
    private val genreDao: GenreDao
) {

    // === movie dao ===================================================================
    fun getMovieWithGenres(movieId: Int): Flow<MovieWithGenres> =
        movieDao.getMovieWithGenres(movieId)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> =
        movieDao.getFavoriteMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }

    suspend fun insertMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef) {
        movieDao.insertMovieGenreCrossRef(crossRef)
    }

    private fun updateMovie(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }

    fun setIsFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        updateMovie(movie)
    }

    // === tv show dao ===================================================================
    fun getTvShowWithGenres(tvShowId: Int): Flow<TvShowWithGenres> =
        tvShowDao.getTvShowWithGenres(tvShowId)

    fun getFavoriteTvShows(): Flow<List<TvShowEntity>> =
        tvShowDao.getFavoriteTvShows()

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) {
        tvShowDao.insertTvShows(tvShows)
    }
    suspend fun insertTvShow(tvShow: TvShowEntity) {
        tvShowDao.insertTvShow(tvShow)
    }

    suspend fun insertTvShowGenreCrossRef(crossRef: TvShowGenreCrossRef) =
        tvShowDao.insertTvShowGenreCrossRef(crossRef)

    private fun updateTvShow(tvShow: TvShowEntity) {
        tvShowDao.updateTvShow(tvShow)
    }

    fun setIsFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        updateTvShow(tvShow)
    }

    // === section dao ===================================================================
    fun getSectionWithMovies(section: Int, filter: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedSection(SortUtils.MOVIES, section, filter)
        return sectionDao.getSectionWithMovies(query)
    }

    fun getSectionWithTvShows(section: Int, filter: String): Flow<List<TvShowEntity>> {
        val query = SortUtils.getSortedSection(SortUtils.TV_SHOWS, section, filter)
        return sectionDao.getSectionWithTvShows(query)
    }

    suspend fun insertSectionMovie(sectionMovie: SectionMovieEntity) {
        sectionDao.insertSectionMovie(sectionMovie)
    }

    suspend fun insertSectionTvShow(sectionTvShow: SectionTvShowEntity) {
        sectionDao.insertSectionTvShow(sectionTvShow)
    }

    suspend fun insertSectionMovieCrossRef(crossRef: SectionMovieCrossRef) {
        sectionDao.insertSectionMovieCrossRef(crossRef)
    }

    suspend fun insertSectionTvShowCrossRef(crossRef: SectionTvShowCrossRef) {
        sectionDao.insertSectionTvShowCrossRef(crossRef)
    }

    // === genre dao ===================================================================
    fun getGenreWithMovies(genreId: Int): Flow<GenreWithMovies> =
        genreDao.getGenreWithMovies(genreId)

    fun getGenreWithTvShows(genreId: Int): Flow<GenreWithTvShows> =
        genreDao.getGenreWithTvShows(genreId)

    suspend fun insertGenre(genres: List<GenreEntity>) {
        genreDao.insertGenre(genres)
    }

}