package com.ardnn.flix.core.data.source.local.room

import androidx.room.*
import com.ardnn.flix.core.data.source.local.entity.GenreEntity
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithTvShows
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :genreId")
    fun getGenreWithMovies(genreId: Int): Flow<GenreWithMovies>

    @Transaction
    @Query("SELECT * FROM genre_entities WHERE genre_id = :genreId")
    fun getGenreWithTvShows(genreId: Int): Flow<GenreWithTvShows>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genres: List<GenreEntity>)
}