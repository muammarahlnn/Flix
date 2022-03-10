package com.ardnn.flix.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardnn.flix.data.source.local.LocalDataSource
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.local.entity.relation.*
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.data.source.remote.response.*
import com.ardnn.flix.util.AppExecutors
import com.ardnn.flix.vo.Resource

class FlixRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFlixRepository {

    override fun getSectionWithMovies(
        page: Int,
        section: Int,
        filter: String
    ): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getSectionWithMovies(section, filter)
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
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
                val moviesEntity = castMovieList(data)
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
    ): LiveData<Resource<List<TvShowEntity>>> {
        return object : NetworkBoundResource<List<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowEntity>> {
                return localDataSource.getSectionWithTvShows(section, filter)
            }

            override fun shouldFetch(data: List<TvShowEntity>?): Boolean {
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
                val tvShowsEntity = castTvShowList(data)
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

    override fun getMovieWithGenres(movieId: Int): LiveData<Resource<MovieWithGenres>> {
        return object : NetworkBoundResource<MovieWithGenres, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieWithGenres> {
                return localDataSource.getMovieWithGenres(movieId)
            }

            override fun shouldFetch(data: MovieWithGenres?): Boolean {
                val isDetailFetched = data?.movie?.isDetailFetched as Boolean
                return !isDetailFetched
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)


            override fun saveCallResult(data: MovieDetailResponse) {

                // get and update movie detail
                val movieDetail = localDataSource.getMovie(data.id)
                movieDetail.overview = data.overview
                movieDetail.runtime = data.runtime
                movieDetail.wallpaperUrl = data.wallpaperUrl
                movieDetail.isDetailFetched = true

                localDataSource.updateMovie(movieDetail)

                // insert genres
                val genresEntity = castGenreList(data.genreList)
                localDataSource.insertGenre(genresEntity)

                // insert movie detail genre cross ref
                val tempMovieId = data.id
                for (genre in genresEntity) {
                    val crossRef = MovieGenreCrossRef(tempMovieId, genre.id)
                    localDataSource.insertMovieGenreCrossRef(crossRef)
                }
            }

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getMovieCredits(movieId)
    }

    override fun setIsFavoriteMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovie(movie, state)
        }
    }

    override fun getTvShowWithGenres(tvShowId: Int): LiveData<Resource<TvShowWithGenres>> {
        return object : NetworkBoundResource<TvShowWithGenres, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowWithGenres> =
                localDataSource.getTvShowWithGenres(tvShowId)

            override fun shouldFetch(data: TvShowWithGenres?): Boolean {
                val isDetailFetched = data?.tvShow?.isDetailFetched as Boolean
                return !isDetailFetched
            }

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                // get and update tv show detail
                val tvShowDetail = localDataSource.getTvShow(data.id)
                tvShowDetail.overview = data.overview
                tvShowDetail.lastAirDate = data.lastAirDate
                tvShowDetail.runtime =
                    if (data.runtimes.isNullOrEmpty()) null
                    else data.runtimes[0]
                tvShowDetail.wallpaperUrl = data.wallpaperUrl
                tvShowDetail.numberOfEpisodes = data.numberOfEpisodes
                tvShowDetail.numberOfSeasons = data.numberOfSeasons
                tvShowDetail.isDetailFetched = true

                localDataSource.updateTvShow(tvShowDetail)

                // insert genres
                val genresEntity = castGenreList(data.genreList)
                localDataSource.insertGenre(genresEntity)

                // insert tv show genres cross ref
                val tempTvShowId = data.id
                for (genre in genresEntity) {
                    val crossRef = TvShowGenreCrossRef(tempTvShowId, genre.id)
                    localDataSource.insertTvShowGenreCrossRef(crossRef)
                }
            }

        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getTvShowCredits(tvShowId)
    }

    override fun setIsFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteTvShow(tvShow, state)
        }
    }

    override fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovies> {
        return localDataSource.getGenreWithMovies(genreId)
    }

    override fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShows> {
        return localDataSource.getGenreWithTvShows(genreId)
    }

    private fun castGenreList(genreList: List<GenreResponse>?): List<GenreEntity> {
        val genres = ArrayList<GenreEntity>()
        for (genre in genreList as List<GenreResponse>) {
            val tempGenre = GenreEntity(
                genre.id,
                genre.name)
            genres.add(tempGenre)
        }

        return genres
    }

    private fun castMovieList(movieList: List<MovieResponse>): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        for (movie in movieList) {
            val tempMovie = MovieEntity(
                id = movie.id,
                title = movie.title,
                releaseDate = movie.releaseDate,
                posterUrl = movie.posterUrl,
                rating = movie.rating,
                popularity = movie.popularity
            )
            movies.add(tempMovie)
        }

        return movies
    }

    private fun castTvShowList(tvShowList: List<TvShowResponse>): List<TvShowEntity> {
        val tvShows = ArrayList<TvShowEntity>()
        for (tvShow in tvShowList) {
            val tempTvShow = TvShowEntity(
                id = tvShow.id,
                title = tvShow.title,
                firstAirDate = tvShow.firstAirDate,
                posterUrl = tvShow.posterUrl,
                rating = tvShow.rating,
                popularity = tvShow.popularity
            )
            tvShows.add(tempTvShow)
        }

        return tvShows
    }

    companion object {
        @Volatile
        private var instance: FlixRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): FlixRepository =
            instance ?: synchronized(this) {
                instance ?: FlixRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}