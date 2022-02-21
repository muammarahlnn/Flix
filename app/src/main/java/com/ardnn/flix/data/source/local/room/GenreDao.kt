package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.relation.GenreWithMovieDetails
import com.ardnn.flix.data.source.local.entity.relation.GenreWithTvShowDetails

@Dao
interface GenreDao {
    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :id")
    fun getGenreWithMovies(id: Int): LiveData<GenreWithMovieDetails>

    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :id")
    fun getGenreWithTvShows(id: Int): LiveData<GenreWithTvShowDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genres: List<GenreEntity>)
}