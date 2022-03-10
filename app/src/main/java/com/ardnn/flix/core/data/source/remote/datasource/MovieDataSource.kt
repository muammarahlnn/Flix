package com.ardnn.flix.core.data.source.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardnn.flix.core.data.source.remote.ApiConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.*
import com.ardnn.flix.core.data.source.remote.service.MovieApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataSource(
    private val apiService: MovieApiService
) : DetailInterface<MovieDetailResponse>, FilmsInterface<MovieResponse> {

    // method to get movie detail
    override fun getDetail(id: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        apiService.getMovieDetails(id, ApiConfig.API_KEY)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMovieDetail.postValue(
                            ApiResponse.success(response.body() as MovieDetailResponse)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultMovieDetail.postValue(
                            ApiResponse.error(
                                response.message(),
                                MovieDetailResponse()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    resultMovieDetail.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            MovieDetailResponse()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }
            })

        return resultMovieDetail
    }

    // method to get now playing movies
    override fun getFirstSectionFilms(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        apiService.getNowPlayingMovies(ApiConfig.API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMovies.postValue(
                            ApiResponse.success(response.body()?.movies as List<MovieResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultMovies.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    // method to get upcoming movies
    override fun getSecondSectionFilms(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        apiService.getUpcomingMovies(ApiConfig.API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMovies.postValue(
                            ApiResponse.success(response.body()?.movies as List<MovieResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultMovies.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    // method to get popular movies
    override fun getThirdSectionFilms(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        apiService.getPopularMovies(ApiConfig.API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMovies.postValue(
                            ApiResponse.success(response.body()?.movies as List<MovieResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultMovies.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    // method to get top rated movies
    override fun getFourthSectionFilms(page: Int): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        apiService.getTopRatedMovies(ApiConfig.API_KEY, page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        resultMovies.postValue(
                            ApiResponse.success(response.body()?.movies as List<MovieResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultMovies.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    resultMovies.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultMovies
    }

    override fun getFilmCredits(id: Int): LiveData<ApiResponse<List<CastResponse>>> {
        EspressoIdlingResource.increment()

        val resultCredits = MutableLiveData<ApiResponse<List<CastResponse>>>()
        apiService.getMovieCredits(id, ApiConfig.API_KEY)
            .enqueue(object : Callback<CreditsResponse> {
                override fun onResponse(
                    call: Call<CreditsResponse>,
                    response: Response<CreditsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultCredits.postValue(
                            ApiResponse.success(response.body()?.cast as List<CastResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultCredits.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                    resultCredits.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }
            })

        return resultCredits
    }

}