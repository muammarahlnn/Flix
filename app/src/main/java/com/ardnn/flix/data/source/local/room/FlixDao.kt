package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.local.entity.relation.*

@Dao
interface FlixDao {
    @Transaction
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM movie_detail_entities where is_favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieDetailEntity>

    @Transaction
    @Query("SELECT * FROM movie_detail_entities WHERE movie_id = :id")
    fun getMovieDetailWithGenres(id: Int): LiveData<MovieDetailWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetailGenreCrossRef(crossRef: MovieDetailGenreCrossRef)

    @Update
    fun updateMovieDetail(movieDetail: MovieDetailEntity)

    @Transaction
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities where is_favorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowDetailEntity>

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities WHERE tv_show_id = :id")
    fun getTvShowDetailWithGenres(id: Int): LiveData<TvShowDetailWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowDetail(tvShowDetail: TvShowDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowDetailGenreCrossRef(crossRef: TvShowDetailGenreCrossRef)

    @Update
    fun updateTvShowDetail(tvShowDetail: TvShowDetailEntity)

    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :id")
    fun getGenreWithMovies(id: Int): LiveData<GenreWithMovieDetails>

    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :id")
    fun getGenreWithTvShows(id: Int): LiveData<GenreWithTvShowDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genres: List<GenreEntity>)
}