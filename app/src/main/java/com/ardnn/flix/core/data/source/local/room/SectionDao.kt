package com.ardnn.flix.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.core.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionTvShowCrossRef
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface SectionDao {

    @Transaction
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getSectionWithMovies(query: SupportSQLiteQuery): Flowable<List<MovieEntity>>

    @Transaction
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getSectionWithTvShows(query: SupportSQLiteQuery): Flowable<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionMovie(sectionMovie: SectionMovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionTvShow(sectionTvShow: SectionTvShowEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionMovieCrossRef(crossRef: SectionMovieCrossRef): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionTvShowCrossRef(crossRef: SectionTvShowCrossRef): Completable

}