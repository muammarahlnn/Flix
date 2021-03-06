package com.ardnn.flix.core.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardnn.flix.core.data.source.local.LocalDataSource
import com.ardnn.flix.core.data.source.local.entity.*
import com.ardnn.flix.core.data.source.local.entity.relation.*
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.core.data.source.remote.response.*
import com.ardnn.flix.core.domain.repository.FlixRepository
import com.ardnn.flix.core.util.AppExecutors

class FakeFlixRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FlixRepository {

    override fun getMovies(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(section, filter), config).build()
            }

            public override fun shouldFetch(moviesEntity: PagedList<MovieEntity>?): Boolean =
                moviesEntity == null || moviesEntity.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
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

            override fun saveCallResult(moviesResponse: List<MovieResponse>) {
                val moviesEntity = castMovieList(moviesResponse, section)
                localDataSource.insertMovies(moviesEntity)
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

    override fun getMovieDetailWithGenres(movieId: Int): LiveData<Resource<MovieWithGenres>> {
        return object : NetworkBoundResource<MovieWithGenres, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieWithGenres> =
                localDataSource.getMovieDetailWithGenres(movieId)

            override fun shouldFetch(movieDetailWithGenres: MovieWithGenres?): Boolean =
                movieDetailWithGenres == null

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(movieDetailResponse: MovieDetailResponse) {
                // insert movie detail
                val movieDetailEntity = MovieEntity(
                    movieDetailResponse.id,
                    movieDetailResponse.title,
                    movieDetailResponse.overview,
                    movieDetailResponse.releaseDate,
                    movieDetailResponse.runtime,
                    movieDetailResponse.rating,
                    movieDetailResponse.posterUrl,
                    movieDetailResponse.wallpaperUrl,
                    false)
                localDataSource.insertMovieDetail(movieDetailEntity)

                // insert genres
                val genresEntity = castGenreList(movieDetailResponse.genreList)
                localDataSource.insertGenre(genresEntity)

                // insert movie detail genre cross ref
                val tempMovieId = movieDetailResponse.id
                for (genre in genresEntity) {
                    val crossRef = MovieGenreCrossRef(tempMovieId, genre.id)
                    localDataSource.insertMovieDetailGenreCrossRef(crossRef)
                }
            }

        }.asLiveData()
    }

    override fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getMovieCredits(movieId)
    }

    override fun getTvShows(page: Int, section: Int, filter: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(section, filter), config).build()
            }

            override fun shouldFetch(tvShowsEntity: PagedList<TvShowEntity>?): Boolean =
                tvShowsEntity == null || tvShowsEntity.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> {
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

            public override fun saveCallResult(tvShowsResponse: List<TvShowResponse>) {
                val tvShowsEntity = castTvShowList(tvShowsResponse, section)
                localDataSource.insertTvShows(tvShowsEntity)
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

    override fun getTvShowDetailWithGenres(tvShowId: Int): LiveData<Resource<TvShowWithGenres>> {
        return object : NetworkBoundResource<TvShowWithGenres, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowWithGenres> =
                localDataSource.getTvShowDetailWithGenres(tvShowId)

            override fun shouldFetch(tvShowDetailWithGenres: TvShowWithGenres?): Boolean =
                tvShowDetailWithGenres == null

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(tvShowDetailResponse: TvShowDetailResponse) {
                // insert tv show detail entity
                val runtime: Int? =
                    if (tvShowDetailResponse.runtimes.isNullOrEmpty()) null
                    else tvShowDetailResponse.runtimes?.get(0)

                val tvShowDetailEntity = TvShowEntity(
                    tvShowDetailResponse.id,
                    tvShowDetailResponse.title,
                    tvShowDetailResponse.overview,
                    tvShowDetailResponse.firstAirDate,
                    tvShowDetailResponse.lastAirDate,
                    runtime,
                    tvShowDetailResponse.rating,
                    tvShowDetailResponse.posterUrl,
                    tvShowDetailResponse.wallpaperUrl,
                    tvShowDetailResponse.numberOfEpisodes,
                    tvShowDetailResponse.numberOfSeasons,
                    false)
                localDataSource.insertTvShowDetail(tvShowDetailEntity)

                // insert genres
                val genresEntity = castGenreList(tvShowDetailResponse.genreList)
                localDataSource.insertGenre(genresEntity)

                // insert tv show detail genres cross ref
                val tempTvShowId = tvShowDetailResponse.id
                for (genre in genresEntity) {
                    val crossRef = TvShowGenreCrossRef(tempTvShowId, genre.id)
                    localDataSource.insertTvShowDetailGenreCrossRef(crossRef)
                }
            }

        }.asLiveData()
    }

    override fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        return remoteDataSource.getTvShowCredits(tvShowId)
    }

    override fun getGenreWithMovies(genreId: Int): LiveData<GenreWithMovies> {
        return localDataSource.getGenreWithMovies(genreId)
    }

    override fun getGenreWithTvShows(genreId: Int): LiveData<GenreWithTvShows> {
        return localDataSource.getGenreWithTvShows(genreId)
    }

    override fun setIsFavoriteMovieDetail(movieDetail: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovieDetail(movieDetail, state)
        }
    }

    override fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteTvShowDetail(tvShowDetail, state)
        }
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

    private fun castMovieList(movieList: List<MovieResponse>, section: Int): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        for (movie in movieList) {
            val tempMovie = MovieEntity(0,
                movie.id,
                movie.title,
                movie.releaseDate,
                movie.posterUrl,
                movie.rating)
            tempMovie.section = section

            movies.add(tempMovie)
        }

        return movies
    }

    private fun castTvShowList(tvShowList: List<TvShowResponse>, section: Int): List<TvShowEntity> {
        val tvShows = ArrayList<TvShowEntity>()
        for (tvShow in tvShowList) {
            val tempTvShow = TvShowEntity(0,
                tvShow.id,
                tvShow.title,
                tvShow.firstAirDate,
                tvShow.posterUrl,
                tvShow.rating)
            tempTvShow.section = section

            tvShows.add(tempTvShow)
        }

        return tvShows
    }
}