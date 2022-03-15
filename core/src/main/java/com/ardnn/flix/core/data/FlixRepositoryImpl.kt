package com.ardnn.flix.core.data

import android.util.Log
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.core.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.MovieGenreCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionMovieCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.SectionTvShowCrossRef
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowGenreCrossRef
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.*
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.repository.FlixRepository
import com.ardnn.flix.core.util.AppExecutors
import com.ardnn.flix.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlixRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FlixRepository {

    override fun getSectionWithMovies(
        page: Int,
        section: Int,
        filter: String
    ): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getSectionWithMovies(section, filter).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return when (section) {
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

    override fun getSectionWithTvShows(
        page: Int,
        section: Int,
        filter: String
    ): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getSectionWithTvShows(section, filter).map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> {
                return when (section) {
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

    override fun getMovieWithGenres(movieId: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieWithGenres(movieId).map {
                    DataMapper.mapMovieWithGenresEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean {
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

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override fun getMovieCredits(movieId: Int): Flow<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getMovieCredits(movieId)
    }

    override fun setIsFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovie(movieEntity, state)
        }
    }

    override fun getTvShowWithGenres(tvShowId: Int): Flow<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<TvShow> {
                return localDataSource.getTvShowWithGenres(tvShowId).map {
                    DataMapper.mapTvShowWithGenresEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean {
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

    override fun getFavoriteTvShows(): Flow<List<TvShow>> {
        return localDataSource.getFavoriteTvShows().map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }
    }

    override fun getTvShowCredits(tvShowId: Int): Flow<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getTvShowCredits(tvShowId)
    }

    override fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntity(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteTvShow(tvShowEntity, state)
        }
    }

    override fun getGenreWithMovies(genreId: Int): Flow<Genre> {
        return localDataSource.getGenreWithMovies(genreId).map {
            DataMapper.mapGenreWithMoviesEntityToDomain(it)
        }
    }

    override fun getGenreWithTvShows(genreId: Int): Flow<Genre> {
        return localDataSource.getGenreWithTvShows(genreId).map {
            DataMapper.mapGenreWithTvShowsEntityToDomain(it)
        }
    }

}