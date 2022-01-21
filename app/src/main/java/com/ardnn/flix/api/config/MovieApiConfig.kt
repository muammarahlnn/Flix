package com.ardnn.flix.api.config

import com.ardnn.flix.api.Const
import com.ardnn.flix.api.callback.MovieDetailCallback
import com.ardnn.flix.api.callback.MoviesCallback
import com.ardnn.flix.api.response.Movie
import com.ardnn.flix.api.response.MovieDetailResponse
import com.ardnn.flix.api.response.MoviesResponse
import com.ardnn.flix.api.service.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApiConfig {
    private val MOVIE_SERVICE: MovieApiService =
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL_MOVIE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)


    // method to get movie detail
    fun getMovieDetail(movieId: Int, callback: MovieDetailCallback) {
        MOVIE_SERVICE.getMovieDetails(movieId, Const.API_KEY)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body() as MovieDetailResponse)
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                }

            })
    }

    // method to get now playing movies
    fun getNowPlayingMovies(page: Int, callback: MoviesCallback) {
        MOVIE_SERVICE.getNowPlayingMovies(Const.API_KEY, page).enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()?.movies != null) {
                            callback.onSuccess(response.body()?.movies as List<Movie>)
                        } else {
                            callback.onFailure("response.body().movies is null")
                        }
                    } else {
                        callback.onFailure("response.body() is null")
                    }
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onFailure("onFailure: ${t.localizedMessage}")
            }
        })
    }
}