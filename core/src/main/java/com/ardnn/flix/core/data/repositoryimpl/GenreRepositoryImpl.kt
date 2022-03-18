package com.ardnn.flix.core.data.repositoryimpl

import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.domain.genre.model.GenreMovies
import com.ardnn.flix.core.domain.genre.model.GenreTvShows
import com.ardnn.flix.core.domain.genre.repository.GenreRepository
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : GenreRepository {
    override fun getGenreWithMovies(genreId: Int): Flow<GenreMovies> =
        localDataSource.getGenreWithMovies(genreId).map {
            DataMapper.mapGenreWithMoviesEntityToDomain(it)
        }

    override fun getGenreWithTvShows(genreId: Int): Flow<GenreTvShows> =
        localDataSource.getGenreWithTvShows(genreId).map {
            DataMapper.mapGenreWithTvShowsEntityToDomain(it)
        }
}