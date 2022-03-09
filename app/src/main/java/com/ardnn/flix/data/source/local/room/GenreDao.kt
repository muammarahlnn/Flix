package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.data.source.local.entity.relation.GenreWithTvShows

@Dao
interface GenreDao {
    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :genreId")
    fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovies>

    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :genreId")
    fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShows>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genres: List<GenreEntity>)
}