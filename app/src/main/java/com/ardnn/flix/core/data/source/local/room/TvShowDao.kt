package com.ardnn.flix.core.data.source.local.room

import androidx.room.*
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowWithGenres
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TvShowDao {

    @Transaction
    @Query("SELECT * FROM tv_show_entities WHERE tv_show_id = :tvShowId")
    fun getTvShow(tvShowId: Int): TvShowEntity

    @Transaction
    @Query("SELECT * FROM tv_show_entities where is_favorite = 1")
    fun getFavoriteTvShows(): Flowable<List<TvShowEntity>>

    @Transaction
    @Query("SELECT * FROM tv_show_entities WHERE tv_show_id = :tvShowId")
    fun getTvShowWithGenres(tvShowId: Int): Flowable<TvShowWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: TvShowEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowGenreCrossRef(crossRef: TvShowGenreCrossRef): Completable

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

}