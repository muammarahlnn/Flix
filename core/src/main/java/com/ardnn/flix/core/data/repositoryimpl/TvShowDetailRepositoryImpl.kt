package com.ardnn.flix.core.data.repositoryimpl

import com.ardnn.flix.core.data.NetworkBoundResource
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.domain.tvshowdetail.repository.TvShowDetailRepository
import com.ardnn.flix.core.util.AppExecutors
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : TvShowDetailRepository {

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetail>> {
        return object : NetworkBoundResource<TvShowDetail, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<TvShowDetail> =
                localDataSource.getTvShowWithGenres(tvShowId).map {
                    DataMapper.mapTvShowWithGenresEntityToDomain(it)
                }

            override fun shouldFetch(data: TvShowDetail?): Boolean {
                val isDetailFetched = data?.isDetailFetched as Boolean
                return !isDetailFetched
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                // get and update tv show detail
                val tvShowEntity = DataMapper.mapTvShowDetailResponseToEntity(data)
                tvShowEntity.isDetailFetched = true
                localDataSource.insertTvShow(tvShowEntity)

                data.genreList?.let {
                    // insert genres
                    val genresEntity = DataMapper.mapGenreResponsesToEntities(data.genreList)
                    localDataSource.insertGenre(genresEntity)

                    // insert tv show genres cross ref
                    val tempTvShowId = data.id
                    for (genre in genresEntity) {
                        val crossRef = TvShowGenreCrossRef(tempTvShowId, genre.id)
                        localDataSource.insertTvShowGenreCrossRef(crossRef)
                    }
                }
            }
        }.asFLow()
    }

    override fun setIsFavoriteTvShow(tvShowDetail: TvShowDetail, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDetailDomainToEntity(tvShowDetail)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteTvShow(tvShowEntity, state)
        }
    }
}