package com.ardnn.flix.data.source.remote

import com.ardnn.flix.data.source.remote.response.*
import com.ardnn.flix.data.source.remote.service.MovieApiService
import com.ardnn.flix.data.source.remote.service.TvShowApiService
import com.ardnn.flix.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource private constructor() {

    companion object {
        private val MOVIE_SERVICE: MovieApiService =
            Retrofit.Builder()
                .baseUrl(Const.BASE_URL_MOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApiService::class.java)

        private val TV_SHOW_SERVICE: TvShowApiService =
            Retrofit.Builder()
                .baseUrl(Const.BASE_URL_TV_SHOW)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TvShowApiService::class.java)

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply {
                    instance = this
                }
            }
    }

    // method to get movie detail
    fun getMovieDetail(movieId: Int, callback: LoadMovieDetailCallback) {
        EspressoIdlingResource.increment()
        MOVIE_SERVICE.getMovieDetails(movieId, Const.API_KEY)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body() as MovieDetailResponse)
                            EspressoIdlingResource.decrement()
                        } else {
                            callback.onFailure("response.body() is null")
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        callback.onFailure(response.message())
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                    EspressoIdlingResource.decrement()
                }
            })
    }

    // method to get now playing movies
    fun getNowPlayingMovies(page: Int, callback: LoadMoviesCallback) {
        EspressoIdlingResource.increment()
        MOVIE_SERVICE.getNowPlayingMovies(Const.API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.movies != null) {
                                callback.onSuccess(response.body()?.movies as List<MovieResponse>)
                                EspressoIdlingResource.decrement()
                            } else {
                                callback.onFailure("response.body().movies is null")
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        callback.onFailure(response.message())
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    // method to get tv show detail
    fun getTvShowDetail(tvShowId: Int, callback: LoadTvShowDetailCallback) {
        EspressoIdlingResource.increment()
        TV_SHOW_SERVICE.getTvShowDetails(tvShowId, Const.API_KEY)
            .enqueue(object : Callback<TvShowDetailResponse> {
                override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body() as TvShowDetailResponse)
                            EspressoIdlingResource.decrement()
                        } else {
                            callback.onFailure("response.body() is null")
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        callback.onFailure(response.message())
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                    EspressoIdlingResource.decrement()
                }
            })
    }

    // method to get on the air tv shows
    fun getOnTheAirTvShows(page: Int, callback: LoadTvShowsCallback) {
        EspressoIdlingResource.increment()
        TV_SHOW_SERVICE.getOnTheAirTvShows(Const.API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.tvShows != null) {
                                callback.onSuccess(response.body()?.tvShows as List<TvShowResponse>)
                                EspressoIdlingResource.decrement()
                            } else {
                                callback.onFailure("response.body().tvShows is null")
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        callback.onFailure(response.message())
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                    EspressoIdlingResource.decrement()
                }

            })
    }

    // callbacks
    interface LoadMovieDetailCallback {
        fun onSuccess(movieDetailResponse: MovieDetailResponse)
        fun onFailure(message: String)
    }

    interface LoadMoviesCallback {
        fun onSuccess(movies: List<MovieResponse>)
        fun onFailure(message: String)
    }

    interface LoadTvShowDetailCallback {
        fun onSuccess(tvShowDetailResponse: TvShowDetailResponse)
        fun onFailure(message: String)
    }

    interface LoadTvShowsCallback {
        fun onSuccess(tvShows: List<TvShowResponse>)
        fun onFailure(message: String)
    }

}