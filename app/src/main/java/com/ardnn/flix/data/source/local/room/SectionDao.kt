package com.ardnn.flix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.data.source.local.entity.relation.SectionTvShowCrossRef

@Dao
interface SectionDao {

    @Transaction
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getSectionWithMovies(query: SupportSQLiteQuery): LiveData<List<MovieEntity>>

    @Transaction
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getSectionWithTvShows(query: SupportSQLiteQuery): LiveData<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionMovie(sectionMovie: SectionMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionTvShow(sectionTvShow: SectionTvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionMovieCrossRef(crossRef: SectionMovieCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionTvShowCrossRef(crossRef: SectionTvShowCrossRef)

}