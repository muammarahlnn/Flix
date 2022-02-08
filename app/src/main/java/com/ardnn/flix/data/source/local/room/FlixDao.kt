package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

@Dao
interface FlixDao {
    @Query("SELECT * FROM movie_entities WHERE section = :section")
    fun getMovies(section: Int): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [MovieDetailEntity::class])
    fun getFavoriteMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieDetailEntity>

    @Query("SELECT * FROM movie_detail_entities WHERE id = :id")
    fun getMovieDetail(id: Int): LiveData<MovieDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Update
    fun updateMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM tv_show_entities WHERE section = :section")
    fun getTvShows(section: Int): DataSource.Factory<Int, TvShowEntity>

    @RawQuery(observedEntities = [TvShowDetailEntity::class])
    fun getFavoriteTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowDetailEntity>

    @Query("SELECT * FROM tv_show_detail_entities WHERE id = :id")
    fun getTvShowDetail(id: Int): LiveData<TvShowDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShowDetail(tvShowDetail: TvShowDetailEntity)

    @Update
    fun updateTvShowDetail(tvShowDetail: TvShowDetailEntity)
}