package com.ardnn.flix.core.data.source.local.room.dao

import androidx.room.*
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithCasts
import com.ardnn.flix.core.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movie_id = :movieId")
    fun getMovieWithGenres(movieId: Int): Flow<MovieWithGenres>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movie_id = :movieId")
    fun getMovieWithCasts(movieId: Int): Flow<MovieWithCasts>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef)

    @Update
    fun updateMovie(movie: MovieEntity)
}