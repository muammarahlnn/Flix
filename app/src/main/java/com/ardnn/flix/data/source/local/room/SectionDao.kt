package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.data.source.local.entity.relation.SectionTvShowCrossRef
import com.ardnn.flix.data.source.local.entity.relation.SectionWithMovies
import com.ardnn.flix.data.source.local.entity.relation.SectionWithTvShows

@Dao
interface SectionDao {

    @Transaction
    @RawQuery(observedEntities = [SectionMovieEntity::class])
    fun getSectionWithMovies(query: SupportSQLiteQuery): LiveData<SectionWithMovies>

    @Transaction
    @RawQuery(observedEntities = [SectionTvShowEntity::class])
    fun getSectionWithTvShows(query: SupportSQLiteQuery): LiveData<SectionWithTvShows>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionMovie(sectionMovie: SectionMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionTvShow(sectionTvShow: SectionTvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionMovieCrossRef(crossRef: SectionMovieCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionTvShowCrossRef(crossRef: SectionTvShowCrossRef)

}