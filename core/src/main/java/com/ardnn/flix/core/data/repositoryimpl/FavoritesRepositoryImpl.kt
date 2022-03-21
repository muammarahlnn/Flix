package com.ardnn.flix.core.data.repositoryimpl

import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.domain.favorites.repository.FavoritesRepository
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.util.AppExecutors
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FavoritesRepository {

    override fun getFavoriteMovies(): Flow<List<MovieDetail>> =
        localDataSource.getFavoriteMovies().map {
            DataMapper.mapMovieEntitiesToMovieDetailDomain(it)
        }

    override fun getFavoriteTvShows(): Flow<List<TvShowDetail>> =
        localDataSource.getFavoriteTvShows().map {
            DataMapper.mapTvShowEntitiesToTvShowDetailDomain(it)
        }

    override fun setIsFavoriteMovie(movie: MovieDetail, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDetailDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovie(movieEntity, state)
        }
    }

    override fun setIsFavoriteTvShow(tvShow: TvShowDetail, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDetailDomainToEntity(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteTvShow(tvShowEntity, state)
        }
    }
}