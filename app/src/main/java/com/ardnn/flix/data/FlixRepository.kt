package com.ardnn.flix.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        private const val SECTION_NOW_PLAYING_MOVIES = 0
        private const val SECTION_TOP_RATED_MOVIES = 1

        private const val SECTION_ON_THE_AIR_TV_SHOWS = 0
        private const val SECTION_TOP_RATED_TV_SHOWS = 1

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

    override fun getNowPlayingMovies(page: Int): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getMovies(SECTION_NOW_PLAYING_MOVIES)

            override fun shouldFetch(moviesEntity: List<MovieEntity>?): Boolean =
                moviesEntity == null || moviesEntity.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getNowPlayingMovies(page)

            override fun saveCallResult(moviesResponse: List<MovieResponse>) {
                val moviesEntity = castMovieList(moviesResponse, SECTION_NOW_PLAYING_MOVIES)
                localDataSource.insertMovies(moviesEntity)
            }

        }.asLiveData()
    }

    override fun getTopRatedMovies(page: Int): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getMovies(SECTION_TOP_RATED_MOVIES)

            override fun shouldFetch(moviesEntity: List<MovieEntity>?): Boolean =
                moviesEntity == null || moviesEntity.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getTopRatedMovies(page)

            override fun saveCallResult(moviesResponse: List<MovieResponse>) {
                val moviesEntity = castMovieList(moviesResponse, SECTION_TOP_RATED_MOVIES)
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

    override fun getOnTheAirTvShows(page: Int): LiveData<Resource<List<TvShowEntity>>> {
        return object : NetworkBoundResource<List<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowEntity>> =
                localDataSource.getTvShows(SECTION_ON_THE_AIR_TV_SHOWS)

            override fun shouldFetch(tvShowsEntity: List<TvShowEntity>?): Boolean =
                tvShowsEntity == null || tvShowsEntity.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getOnTheAirTvShows(page)

            override fun saveCallResult(tvShowsResponse: List<TvShowResponse>) {
                val tvShowsEntity = castTvShowList(tvShowsResponse, SECTION_ON_THE_AIR_TV_SHOWS)
                localDataSource.insertTvShows(tvShowsEntity)
            }

        }.asLiveData()
    }

    override fun getTopRatedTvShows(page: Int): LiveData<Resource<List<TvShowEntity>>> {
        return object : NetworkBoundResource<List<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowEntity>> =
                localDataSource.getTvShows(SECTION_TOP_RATED_TV_SHOWS)

            override fun shouldFetch(tvShowsEntity: List<TvShowEntity>?): Boolean =
                tvShowsEntity == null || tvShowsEntity.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getTopRatedTvShows(page)

            override fun saveCallResult(tvShowsResponse: List<TvShowResponse>) {
                val tvShowsEntity = castTvShowList(tvShowsResponse, SECTION_TOP_RATED_TV_SHOWS)
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