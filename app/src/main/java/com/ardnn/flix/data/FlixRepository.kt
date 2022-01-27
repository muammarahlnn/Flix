package com.ardnn.flix.data

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.data.source.remote.response.*

class FlixRepository private constructor(
    private val remoteDataSource: RemoteDataSource
) : FlixDataSource{

    private val _isLoadFailure = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()

    companion object {
        private const val TAG = "FlixRepository"

        @Volatile
        private var instance: FlixRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): FlixRepository =
            instance ?: synchronized(this) {
                instance ?: FlixRepository(remoteDataSource).apply {
                    instance = this
                }
            }
    }

    override fun getMovies(page: Int): LiveData<List<MovieEntity>> {
        // show progressbar
        _isLoading.postValue(true)

        val movieResults = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getNowPlayingMovies(page, object : RemoteDataSource.LoadMoviesCallback {
            override fun onSuccess(movies: List<MovieResponse>) {
                // hide progressbar
                _isLoading.postValue(false)

                // success fetch data
                _isLoadFailure.postValue(false)

                // cast MovieResponse to MovieEntity
                val movieList = ArrayList<MovieEntity>()
                for (movie in movies) {
                    val tempMovie = MovieEntity(
                        movie.id,
                        movie.title,
                        movie.releaseDate,
                        movie.posterUrl,
                        movie.rating)

                    movieList.add(tempMovie)
                }
                movieResults.postValue(movieList)
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.postValue(false)

                // unable to fetch data
                _isLoadFailure.postValue(true)
                Log.d(TAG, message)
            }
        })

        return movieResults
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieDetailEntity> {
        // show progressbar
        _isLoading.postValue(true)

        val movieDetailResult = MutableLiveData<MovieDetailEntity>()
        remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.LoadMovieDetailCallback {
            override fun onSuccess(movieDetailResponse: MovieDetailResponse) {
                // hide progressbar
                _isLoading.postValue(false)

                // success fetch data
                _isLoadFailure.postValue(false)

                // cast movieDetailResponse to MovieDetailEntity
                val movieDetail = MovieDetailEntity(
                    movieDetailResponse.id,
                    movieDetailResponse.title,
                    movieDetailResponse.overview,
                    movieDetailResponse.releaseDate,
                    movieDetailResponse.runtime,
                    movieDetailResponse.rating,
                    movieDetailResponse.posterUrl,
                    movieDetailResponse.wallpaperUrl,
                    castGenreList(movieDetailResponse.genreList))

                movieDetailResult.postValue(movieDetail)
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.postValue(false)

                // unable fetch data
                _isLoadFailure.postValue(true)
                Log.d(TAG, message)
            }
        })

        return movieDetailResult
    }

    override fun getTvShows(page: Int): LiveData<List<TvShowEntity>> {
        // show progressbar
        _isLoading.postValue(true)

        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
        remoteDataSource.getOnTheAirTvShows(page, object : RemoteDataSource.LoadTvShowsCallback {
            override fun onSuccess(tvShows: List<TvShowResponse>) {
                // hide progressbar
                _isLoading.postValue(false)

                // success fetch data
                _isLoadFailure.postValue(false)

                // cast TvShowResponse to TvShowEntity
                val tvShowList = ArrayList<TvShowEntity>()
                for (tvShow in tvShows) {
                    val tempTvShow = TvShowEntity(
                        tvShow.id,
                        tvShow.title,
                        tvShow.firstAirDate,
                        tvShow.posterUrl,
                        tvShow.rating)

                    tvShowList.add(tempTvShow)
                }
                tvShowResults.postValue(tvShowList)
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.postValue(false)

                // unable fetch data
                _isLoadFailure.postValue(true)
                Log.d(TAG, message)
            }
        })

        return tvShowResults
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<TvShowDetailEntity> {
        // show progressbar
        _isLoading.postValue(true)

        val tvShowDetailResult = MutableLiveData<TvShowDetailEntity>()
        remoteDataSource.getTvShowDetail(tvShowId, object : RemoteDataSource.LoadTvShowDetailCallback {
            override fun onSuccess(tvShowDetailResponse: TvShowDetailResponse) {
                // hide progressbar
                _isLoading.postValue(false)

                // success fetch data
                _isLoadFailure.postValue(false)

                // cast TvShowDetailResponse to TvShowDetailEntity
                val tvShowDetail = TvShowDetailEntity(
                    tvShowDetailResponse.id,
                    tvShowDetailResponse.title,
                    tvShowDetailResponse.overview,
                    tvShowDetailResponse.firstAirDate,
                    tvShowDetailResponse.lastAirDate,
                    tvShowDetailResponse.runtimes,
                    tvShowDetailResponse.rating,
                    tvShowDetailResponse.posterUrl,
                    tvShowDetailResponse.wallpaperUrl,
                    tvShowDetailResponse.numberOfEpisodes,
                    tvShowDetailResponse.numberOfSeasons,
                    castGenreList(tvShowDetailResponse.genreList))

                tvShowDetailResult.postValue(tvShowDetail)
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.postValue(false)

                // unable fetch data
                _isLoadFailure.postValue(true)
                Log.d(TAG, message)
            }
        })

        return tvShowDetailResult
    }

    override fun getIsLoadFailure(): LiveData<Boolean> =
        _isLoadFailure

    override fun getIsLoading(): LiveData<Boolean> =
        _isLoading

    private fun castGenreList(genreList: List<GenreResponse>?): List<GenreEntity> {
        val tempList = ArrayList<GenreEntity>()
        for (genre in genreList as List<GenreResponse>) {
            val tempGenre = GenreEntity(
                genre.id,
                genre.name)
            tempList.add(tempGenre)
        }

        return tempList
    }
}