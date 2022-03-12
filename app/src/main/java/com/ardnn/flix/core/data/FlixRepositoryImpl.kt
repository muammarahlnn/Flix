package com.ardnn.flix.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.SectionMovieEntity
import com.ardnn.flix.core.data.source.local.entity.SectionTvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.*
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.*
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.repository.FlixRepository
import com.ardnn.flix.core.util.AppExecutors
import com.ardnn.flix.core.util.DataMapper
import com.ardnn.flix.core.vo.Resource

class FlixRepositoryImpl private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FlixRepository {

    override fun getSectionWithMovies(
        page: Int,
        section: Int,
        filter: String
    ): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Movie>> {
                return Transformations.map(localDataSource.getSectionWithMovies(section, filter)) {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
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

            override fun saveCallResult(data: List<MovieResponse>) {
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

        }.asLiveData()
    }

    override fun getSectionWithTvShows(
        page: Int,
        section: Int,
        filter: String
    ): LiveData<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShow>> {
                return Transformations.map(localDataSource.getSectionWithTvShows(section, filter)) {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> {
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

            override fun saveCallResult(data: List<TvShowResponse>) {
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

        }.asLiveData()
    }

    override fun getMovieWithGenres(movieId: Int): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<Movie> {
                return Transformations.map(localDataSource.getMovieWithGenres(movieId)) {
                    DataMapper.mapMovieWithGenresEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                val isDetailFetched = data?.isDetailFetched as Boolean
                return !isDetailFetched
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {
                // get and update movie detail
                val movieDetail = localDataSource.getMovie(data.id)
                movieDetail.overview = data.overview ?: ""
                movieDetail.runtime = data.runtime ?: 0
                movieDetail.wallpaperUrl = data.wallpaperUrl ?: ""
                movieDetail.isDetailFetched = true

                localDataSource.updateMovie(movieDetail)

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

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        return Transformations.map(localDataSource.getFavoriteMovies()) {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getMovieCredits(movieId)
    }

    override fun setIsFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovie(movieEntity, state)
        }
    }

    override fun getTvShowWithGenres(tvShowId: Int): LiveData<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShow> {
                return Transformations.map(localDataSource.getTvShowWithGenres(tvShowId)) {
                    DataMapper.mapTvShowWithGenresEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: TvShow?): Boolean {
                val isDetailFetched = data?.isDetailFetched as Boolean
                return !isDetailFetched
            }

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                // get and update tv show detail
                val tvShowDetail = localDataSource.getTvShow(data.id)
                tvShowDetail.overview = data.overview ?: ""
                tvShowDetail.lastAirDate = data.lastAirDate ?: ""
                tvShowDetail.runtime =
                    if (data.runtimes.isNullOrEmpty()) 0
                    else data.runtimes[0]
                tvShowDetail.wallpaperUrl = data.wallpaperUrl ?: ""
                tvShowDetail.numberOfEpisodes = data.numberOfEpisodes ?: 0
                tvShowDetail.numberOfSeasons = data.numberOfSeasons ?: 0
                tvShowDetail.isDetailFetched = true

                localDataSource.updateTvShow(tvShowDetail)

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

        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<List<TvShow>> {
        return Transformations.map(localDataSource.getFavoriteTvShows()) {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }
    }

    override fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getTvShowCredits(tvShowId)
    }

    override fun setIsFavoriteTvShow(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntity(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteTvShow(tvShowEntity, state)
        }
    }

    override fun getGenreWithMovies(genreId: Int): LiveData<Genre> {
        return Transformations.map(localDataSource.getGenreWithMovies(genreId)) {
            DataMapper.mapGenreWithMoviesEntityToDomain(it)
        }
    }

    override fun getGenreWithTvShows(genreId: Int): LiveData<Genre> {
        return Transformations.map(localDataSource.getGenreWithTvShows(genreId)) {
            DataMapper.mapGenreWithTvShowsEntityToDomain(it)
        }
    }

    companion object {
        @Volatile
        private var instance: FlixRepositoryImpl? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): FlixRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: FlixRepositoryImpl(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}