package com.ardnn.flix.core.data.repositoryimpl

import com.ardnn.flix.core.data.NetworkBoundResource
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.SectionTvShowCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.TvShowResponse
import com.ardnn.flix.core.domain.tvshows.model.TvShow
import com.ardnn.flix.core.domain.tvshows.repository.TvShowsRepository
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : TvShowsRepository {

    override fun getTvShows(page: Int, section: Int, filter: String): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<TvShow>> =
                localDataSource.getSectionWithTvShows(section, filter).map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                when (section) {
                    0 -> { // airing today
                        remoteDataSource.getAiringTodayTvShows(page)
                    }
                    1 -> { // on the air
                        remoteDataSource.getOnTheAirTvShows(page)
                    }
                    2 -> { // popular
                        remoteDataSource.getPopularTvShows(page)
                    }
                    3 -> { // top rated
                        remoteDataSource.getTopRatedTvShows(page)
                    }
                    else -> { // default
                        remoteDataSource.getAiringTodayTvShows(page)
                    }
                }

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                // insert tv shows
                val tvShowsEntity = DataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertTvShows(tvShowsEntity)

                // insert tv show genre cross ref
                for (tvShow in data) {
                    val genreIds = tvShow.genreIds
                    if (genreIds != null) {
                        for (genreId in genreIds) {
                            val crossRef = TvShowGenreCrossRef(tvShow.id, genreId)
                            localDataSource.insertTvShowGenreCrossRef(crossRef)
                        }
                    }
                }

                // insert section
                val sections = arrayOf("Airing Today", "On The Air", "Popular", "Top Rated")
                val sectionEntity = SectionTvShowEntity(section, sections[section])
                localDataSource.insertSectionTvShow(sectionEntity)

                // insert section tv show cross ref
                for (tvShow in tvShowsEntity) {
                    val crossRef = SectionTvShowCrossRef(section, tvShow.id)
                    localDataSource.insertSectionTvShowCrossRef(crossRef)
                }
            }
        }.asFLow()
    }
}