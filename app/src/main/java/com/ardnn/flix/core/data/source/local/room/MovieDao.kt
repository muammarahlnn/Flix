package com.ardnn.flix.core.data.source.local.room

import androidx.room.*
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithGenres
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movie_id = :movieId")
    fun getMovie(movieId: Int): MovieEntity

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE is_favorite = 1")
    fun getFavoriteMovies(): Flowable<List<MovieEntity>>

    @Transaction
    @Query("SELECT * FROM movie_entities WHERE movie_id = :movieId")
    fun getMovieWithGenres(movieId: Int): Flowable<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef): Completable

    @Update
    fun updateMovie(movie: MovieEntity): Completable
}