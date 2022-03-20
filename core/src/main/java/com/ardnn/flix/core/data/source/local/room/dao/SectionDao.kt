package com.ardnn.flix.core.data.source.local.room.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.core.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionTvShowCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {

    @Transaction
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getSectionWithMovies(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Transaction
    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getSectionWithTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSectionMovie(sectionMovie: SectionMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSectionTvShow(sectionTvShow: SectionTvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSectionMovieCrossRef(crossRef: SectionMovieCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSectionTvShowCrossRef(crossRef: SectionTvShowCrossRef)

}