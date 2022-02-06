package com.ardnn.flix.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardnn.flix.data.source.local.LocalDataSource
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.data.source.remote.response.*
import com.ardnn.flix.utils.AppExecutors
import com.ardnn.flix.vo.Resource

class FlixRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FlixDataSource{

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

    override fun getMovies(page: Int, section: Int): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(section), config).build()
            }

            override fun shouldFetch(moviesEntity: PagedList<MovieEntity>?): Boolean =
                moviesEntity == null || moviesEntity.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return when (section) {
                    0 -> { // now playing
                        remoteDataSource.getNowPlayingMovies(page)
                    }
                    1 -> { // top rated
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

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetailEntity>> {
        return object : NetworkBoundResource<MovieDetailEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieDetailEntity> =
                localDataSource.getMovieDetail(movieId)

            override fun shouldFetch(movieDetailEntity: MovieDetailEntity?): Boolean =
                movieDetailEntity == null

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(movieDetailResponse: MovieDetailResponse) {
                val movieDetailEntity = MovieDetailEntity(
                    movieDetailResponse.id,
                    movieDetailResponse.title,
                    movieDetailResponse.overview,
                    movieDetailResponse.releaseDate,
                    movieDetailResponse.runtime,
                    movieDetailResponse.rating,
                    movieDetailResponse.posterUrl,
                    movieDetailResponse.wallpaperUrl,
                    castGenreList(movieDetailResponse.genreList),
                    false)

                localDataSource.insertMovieDetail(movieDetailEntity)
            }

        }.asLiveData()
    }

    override fun getTvShows(page: Int, section: Int): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(section), config).build()
            }

            override fun shouldFetch(tvShowsEntity: PagedList<TvShowEntity>?): Boolean =
                tvShowsEntity == null || tvShowsEntity.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> {
                return when (section) {
                    0 -> { // on the air
                        remoteDataSource.getOnTheAirTvShows(page)
                    }
                    1 -> { // top rated
                        remoteDataSource.getTopRatedTvShows(page)
                    }
                    else -> { // default
                        remoteDataSource.getOnTheAirTvShows(page)
                    }
                }
            }

            override fun saveCallResult(tvShowsResponse: List<TvShowResponse>) {
                val tvShowsEntity = castTvShowList(tvShowsResponse, section)
                localDataSource.insertTvShows(tvShowsEntity)
            }

        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowDetailEntity>> {
        return object : NetworkBoundResource<TvShowDetailEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowDetailEntity> =
                localDataSource.getTvShowDetail(tvShowId)

            override fun shouldFetch(tvShowDetailEntity: TvShowDetailEntity?): Boolean =
                tvShowDetailEntity == null

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(tvShowDetailResponse: TvShowDetailResponse) {
                val tvShowDetailEntity = TvShowDetailEntity(
                    tvShowDetailResponse.id,
                    tvShowDetailResponse.title,
                    tvShowDetailResponse.overview,
                    tvShowDetailResponse.firstAirDate,
                    tvShowDetailResponse.lastAirDate,
                    tvShowDetailResponse.runtimes?.get(0),
                    tvShowDetailResponse.rating,
                    tvShowDetailResponse.posterUrl,
                    tvShowDetailResponse.wallpaperUrl,
                    tvShowDetailResponse.numberOfEpisodes,
                    tvShowDetailResponse.numberOfSeasons,
                    castGenreList(tvShowDetailResponse.genreList),
                    false)

                localDataSource.insertTvShowDetail(tvShowDetailEntity)
            }

        }.asLiveData()
    }

    override fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setIsFavoriteMovieDetail(movieDetail, state)
        }
    }

    override fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, state: Boolean) {
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
            val tempMovie = MovieEntity(
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
            val tempTvShow = TvShowEntity(
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