package com.ardnn.flix.core.data.repositoryimpl

import com.ardnn.flix.core.data.NetworkBoundResource
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.core.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.MovieResponse
import com.ardnn.flix.core.domain.movies.model.Movie
import com.ardnn.flix.core.domain.movies.repository.MoviesRepository
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MoviesRepository {

    override fun getMovies(page: Int, section: Int, filter: String): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getSectionWithMovies(section, filter).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                when (section) {
                    0 -> { // now playing
                        remoteDataSource.getNowPlayingMovies(page)
                    }
                    1 -> { // upcoming
                        remoteDataSource.getUpcomingMovies(page)
                    }
                    2 -> { // popular
                        remoteDataSource.getPopularMovies(page)
                    }
                    3 -> { // top rated
                        remoteDataSource.getTopRatedMovies(page)
                    }
                    else -> { // default
                        remoteDataSource.getNowPlayingMovies(page)
                    }
                }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                // insert movies
                val moviesEntity = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(moviesEntity)

                // insert movie genre cross ref
                for (movie in data) {
                    val genreIds = movie.genreIds
                    if (genreIds != null) {
                        for (genreId in genreIds) {
                            val crossRef = MovieGenreCrossRef(movie.id, genreId)
                            localDataSource.insertMovieGenreCrossRef(crossRef)
                        }
                    }
                }

                // insert section
                val sections = arrayOf("Now Playing", "Upcoming", "Popular", "Top Rated")
                val sectionEntity = SectionMovieEntity(section, sections[section])
                localDataSource.insertSectionMovie(sectionEntity)

                // insert section movie cross ref
                for (movie in moviesEntity) {
                    val crossRef = SectionMovieCrossRef(section, movie.id)
                    localDataSource.insertSectionMovieCrossRef(crossRef)
                }
            }
        }.asFLow()
    }
}