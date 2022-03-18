package com.ardnn.flix.core.data.repositoryimpl

import com.ardnn.flix.core.data.NetworkBoundResource
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.moviedetail.repository.MovieDetailRepository
import com.ardnn.flix.core.util.AppExecutors
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDetailRepository {

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return object : NetworkBoundResource<MovieDetail, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<MovieDetail> =
                localDataSource.getMovieWithGenres(movieId).map {
                    DataMapper.mapMovieWithGenresEntityToDomain(it)
                }

            override fun shouldFetch(data: MovieDetail?): Boolean {
                val isDetailFetched = data?.isDetailFetched as Boolean
                return !isDetailFetched
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                // get and update movie detail
                val movieEntity = DataMapper.mapMovieDetailResponseToEntity(data)
                movieEntity.isDetailFetched = true
                localDataSource.insertMovie(movieEntity)

                data.genreList?.let {
                    // insert genres
                    val genresEntity = DataMapper.mapGenreResponsesToEntities(data.genreList)
                    localDataSource.insertGenre(genresEntity)

                    // insert movie detail genre cross ref
                    val tempMovieId = data.id
                    for (genre in genresEntity) {
                        val crossRef = MovieGenreCrossRef(tempMovieId, genre.id)
                        localDataSource.insertMovieGenreCrossRef(crossRef)
                    }
                }
            }
        }.asFLow()
    }

    override fun setIsFavoriteMovie(movieDetail: MovieDetail, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDetailDomainToEntity(movieDetail)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovie(movieEntity, state)
        }
    }
}