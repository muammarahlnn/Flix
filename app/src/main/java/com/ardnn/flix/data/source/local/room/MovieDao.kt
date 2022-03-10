package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.data.source.local.entity.relation.MovieWithGenres

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movie_id = :movieId")
    fun getMovie(movieId: Int): MovieEntity

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movie_id = :movieId")
    fun getMovieWithGenres(movieId: Int): LiveData<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef)

    @Update
    fun updateMovie(movie: MovieEntity)
}