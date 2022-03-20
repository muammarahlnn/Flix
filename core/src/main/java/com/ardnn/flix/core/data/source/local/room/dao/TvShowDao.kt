package com.ardnn.flix.core.data.source.local.room.dao

import androidx.room.*
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowWithCasts
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowWithGenres
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Transaction
    @Query("SELECT * FROM tv_show_entities WHERE tv_show_id = :tvShowId")
    fun getTvShowWithGenres(tvShowId: Int): Flow<TvShowWithGenres>

    @Transaction
    @Query("SELECT * FROM tv_show_entities WHERE tv_show_id = :tvShowId")
    fun getTvShowWithCasts(tvShowId: Int): Flow<TvShowWithCasts>

    @Transaction
    @Query("SELECT * FROM tv_show_entities where is_favorite = 1")
    fun getFavoriteTvShows(): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowGenreCrossRef(crossRef: TvShowGenreCrossRef)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

}