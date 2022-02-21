package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.relation.MovieDetailGenreCrossRef
import com.ardnn.flix.data.source.local.entity.relation.MovieDetailWithGenres

@Dao
interface MovieDao {
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
}