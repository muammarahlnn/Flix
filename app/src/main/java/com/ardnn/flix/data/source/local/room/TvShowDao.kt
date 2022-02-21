package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.local.entity.relation.TvShowDetailGenreCrossRef
import com.ardnn.flix.data.source.local.entity.relation.TvShowDetailWithGenres

@Dao
interface TvShowDao {
    @Transaction
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities where is_favorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowDetailEntity>

    @Transaction
    @Query("SELECT * FROM tv_show_detail_entities WHERE tv_show_id = :id")
    fun getTvShowDetailWithGenres(id: Int): LiveData<TvShowDetailWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowDetail(tvShowDetail: TvShowDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowDetailGenreCrossRef(crossRef: TvShowDetailGenreCrossRef)

    @Update
    fun updateTvShowDetail(tvShowDetail: TvShowDetailEntity)
}