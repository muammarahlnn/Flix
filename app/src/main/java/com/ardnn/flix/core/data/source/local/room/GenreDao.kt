package com.ardnn.flix.core.data.source.local.room

import androidx.room.*
import com.ardnn.flix.core.data.source.local.entity.GenreEntity
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithTvShows
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GenreDao {
    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :genreId")
    fun getGenreWithMovies(genreId: Int): Flowable<GenreWithMovies>

    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :genreId")
    fun getGenreWithTvShows(genreId: Int): Flowable<GenreWithTvShows>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genres: List<GenreEntity>): Completable

    @Update
    fun updateGenre(genre: GenreEntity)
}