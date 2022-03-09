package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.data.source.local.entity.relation.TvShowWithGenres

@Dao
interface TvShowDao {

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities WHERE tv_show_id = :tvShowId")
    fun getTvShow(tvShowId: Int): TvShowEntity

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities where is_favorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities WHERE tv_show_id = :tvShowId")
    fun getTvShowWithGenres(tvShowId: Int): LiveData<TvShowWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowGenreCrossRef(crossRef: TvShowGenreCrossRef)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

}