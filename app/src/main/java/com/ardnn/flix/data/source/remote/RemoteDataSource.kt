package com.ardnn.flix.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardnn.flix.BuildConfig
import com.ardnn.flix.data.source.remote.response.*
import com.ardnn.flix.data.source.remote.service.MovieApiService
import com.ardnn.flix.data.source.remote.service.PersonApiService
import com.ardnn.flix.data.source.remote.service.TvShowApiService
import com.ardnn.flix.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource private constructor() {

    // method to get movie detail
    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        MOVIE_SERVICE.getMovieDetails(movieId, API_KEY)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            resultMovieDetail.postValue(ApiResponse.success(response.body() as MovieDetailResponse))
                            EspressoIdlingResource.decrement()
                        } else {
                            resultMovieDetail.postValue(ApiResponse.error(
                                "response.body() is null",
                                MovieDetailResponse()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultMovieDetail.postValue(ApiResponse.error(
                            response.message(),
                            MovieDetailResponse()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    resultMovieDetail.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        MovieDetailResponse()
                    ))
                    EspressoIdlingResource.decrement()
                }
            })

        return resultMovieDetail
    }

    // method to get now playing movies
    fun getNowPlayingMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        MOVIE_SERVICE.getNowPlayingMovies(API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.movies != null) {
                                resultMovies.postValue(ApiResponse.success(response.body()?.movies as List<MovieResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultMovies.postValue(ApiResponse.error(
                                    "response.body().movies is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultMovies.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultMovies.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    // method to get upcoming movies
    fun getUpcomingMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        MOVIE_SERVICE.getUpcomingMovies(API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.movies != null) {
                                resultMovies.postValue(ApiResponse.success(response.body()?.movies as List<MovieResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultMovies.postValue(ApiResponse.error(
                                    "response.body().movies is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultMovies.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultMovies.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }
            })

        return resultMovies
    }

    // method to get popular movies
    fun getPopularMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        MOVIE_SERVICE.getPopularMovies(API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.movies != null) {
                                resultMovies.postValue(ApiResponse.success(response.body()?.movies as List<MovieResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultMovies.postValue(ApiResponse.error(
                                    "response.body().movies is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultMovies.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultMovies.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    // method to get top rated movies
    fun getTopRatedMovies(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        MOVIE_SERVICE.getTopRatedMovies(API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.movies != null) {
                                resultMovies.postValue(ApiResponse.success(response.body()?.movies as List<MovieResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultMovies.postValue(ApiResponse.error(
                                    "response.body().movies is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultMovies.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultMovies.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    // method to get movie credits
    fun getMovieCredits(movieId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        EspressoIdlingResource.increment()

        val resultCredits = MutableLiveData<ApiResponse<List<CastResponse>>>()
        MOVIE_SERVICE.getMovieCredits(movieId, API_KEY)
            .enqueue(object : Callback<CreditsResponse> {
                override fun onResponse(
                    call: Call<CreditsResponse>,
                    response: Response<CreditsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.cast != null) {
                                resultCredits.postValue(
                                    ApiResponse.success(response.body()?.cast as List<CastResponse>)
                                )
                                EspressoIdlingResource.decrement()
                            } else {
                                resultCredits.postValue(
                                    ApiResponse.error(
                                        "response.body().cast is null",
                                        listOf())
                                )
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultCredits.postValue(
                                ApiResponse.error(
                                    "response.body() is null",
                                    listOf())
                            )
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultCredits.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf())
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                    resultCredits.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf())
                    )
                    EspressoIdlingResource.decrement()
                }
            })

        return resultCredits
    }

    // method to get tv show detail
    fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultTvShowDetail = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        TV_SHOW_SERVICE.getTvShowDetails(tvShowId, API_KEY)
            .enqueue(object : Callback<TvShowDetailResponse> {
                override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            resultTvShowDetail.postValue(ApiResponse.success(response.body() as TvShowDetailResponse))
                            EspressoIdlingResource.decrement()
                        } else {
                            resultTvShowDetail.postValue(ApiResponse.error(
                                "response.body() is null",
                                TvShowDetailResponse()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultTvShowDetail.postValue(ApiResponse.error(
                            response.message(),
                            TvShowDetailResponse()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                    resultTvShowDetail.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        TvShowDetailResponse()
                    ))
                    EspressoIdlingResource.decrement()
                }
            })

        return resultTvShowDetail
    }

    // method to get airing today tv shows
    fun getAiringTodayTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        TV_SHOW_SERVICE.getAiringTodayTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.tvShows != null) {
                                resultTvShows.postValue(ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultTvShows.postValue(ApiResponse.error(
                                    "response.body().tvShows is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultTvShows.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultTvShows.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // method to get on the air tv shows
    fun getOnTheAirTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        TV_SHOW_SERVICE.getOnTheAirTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.tvShows != null) {
                                resultTvShows.postValue(ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultTvShows.postValue(ApiResponse.error(
                                    "response.body().tvShows is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultTvShows.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultTvShows.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // method to get popular tv shows
    fun getPopularTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        TV_SHOW_SERVICE.getPopularTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.tvShows != null) {
                                resultTvShows.postValue(ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultTvShows.postValue(ApiResponse.error(
                                    "response.body().tvShows is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultTvShows.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultTvShows.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // method to get top rated tv shows
    fun getTopRatedTvShows(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        TV_SHOW_SERVICE.getTopRatedTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.tvShows != null) {
                                resultTvShows.postValue(ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>))
                                EspressoIdlingResource.decrement()
                            } else {
                                resultTvShows.postValue(ApiResponse.error(
                                    "response.body().tvShows is null",
                                    listOf()
                                ))
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultTvShows.postValue(ApiResponse.error(
                                "response.body() is null",
                                listOf()
                            ))
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultTvShows.postValue(ApiResponse.error(
                            response.message(),
                            listOf()
                        ))
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(ApiResponse.error(
                        "onFailure: ${t.localizedMessage}",
                        listOf()
                    ))
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // method to get tv show credits
    fun getTvShowCredits(tvShowId: Int): LiveData<ApiResponse<List<CastResponse>>> {
        EspressoIdlingResource.increment()

        val resultCredits = MutableLiveData<ApiResponse<List<CastResponse>>>()
        TV_SHOW_SERVICE.getTvShowCredits(tvShowId, API_KEY)
            .enqueue(object : Callback<CreditsResponse> {
                override fun onResponse(
                    call: Call<CreditsResponse>,
                    response: Response<CreditsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.cast != null) {
                                resultCredits.postValue(
                                    ApiResponse.success(response.body()?.cast as List<CastResponse>)
                                )
                                EspressoIdlingResource.decrement()
                            } else {
                                resultCredits.postValue(
                                    ApiResponse.error(
                                        "response.body().cast is null",
                                        listOf())
                                )
                                EspressoIdlingResource.decrement()
                            }
                        } else {
                            resultCredits.postValue(
                                ApiResponse.error(
                                    "response.body() is null",
                                    listOf())
                            )
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultCredits.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf())
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                    resultCredits.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf())
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultCredits
    }
    // method to get person detail
    fun getPersonDetail(personId: Int): LiveData<ApiResponse<PersonResponse>> {
        EspressoIdlingResource.increment()

        val resultPersonDetail = MutableLiveData<ApiResponse<PersonResponse>>()
        PERSON_SERVICE.getPersonDetail(personId, API_KEY)
            .enqueue(object : Callback<PersonResponse> {
                override fun onResponse(
                    call: Call<PersonResponse>,
                    response: Response<PersonResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            resultPersonDetail.postValue(
                                ApiResponse.success(response.body() as PersonResponse)
                            )
                            EspressoIdlingResource.decrement()
                        } else {
                            resultPersonDetail.postValue(
                                ApiResponse.error(
                                    "response.body() is null",
                                    PersonResponse())
                            )
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultPersonDetail.postValue(
                            ApiResponse.error(
                                response.message(),
                                PersonResponse())
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                    resultPersonDetail.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            PersonResponse())
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultPersonDetail
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
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

        private val PERSON_SERVICE: PersonApiService =
            Retrofit.Builder()
                .baseUrl(Const.BASE_URL_PERSON)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PersonApiService::class.java)

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply {
                    instance = this
                }
            }
    }
}