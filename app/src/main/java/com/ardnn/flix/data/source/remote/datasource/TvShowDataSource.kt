package com.ardnn.flix.data.source.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardnn.flix.BuildConfig
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.*
import com.ardnn.flix.data.source.remote.service.TvShowApiService
import com.ardnn.flix.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDataSource(
    private val apiService: TvShowApiService
) : DetailInterface<TvShowDetailResponse>, FilmsInterface<TvShowResponse> {

    override fun getDetail(id: Int): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultTvShowDetail = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        apiService.getTvShowDetails(id, API_KEY)
            .enqueue(object : Callback<TvShowDetailResponse> {
                override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        resultTvShowDetail.postValue(
                            ApiResponse.success(response.body() as TvShowDetailResponse)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultTvShowDetail.postValue(
                            ApiResponse.error(
                                response.message(),
                                TvShowDetailResponse()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                    resultTvShowDetail.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            TvShowDetailResponse()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShowDetail
    }

    // get airing today tv shows
    override fun getFirstSectionFilms(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        apiService.getAiringTodayTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultTvShows.postValue(
                            ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultTvShows.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // get on the air tv shows
    override fun getSecondSectionFilms(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        apiService.getOnTheAirTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultTvShows.postValue(
                            ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultTvShows.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // get popular tv shows
    override fun getThirdSectionFilms(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        apiService.getPopularTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultTvShows.postValue(
                            ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultTvShows.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    // get top rated tv shows
    override fun getFourthSectionFilms(page: Int): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        apiService.getTopRatedTvShows(API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        resultTvShows.postValue(
                            ApiResponse.success(response.body()?.tvShows as List<TvShowResponse>)
                        )
                        EspressoIdlingResource.decrement()
                    } else {
                        resultTvShows.postValue(
                            ApiResponse.error(
                                response.message(),
                                listOf()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    resultTvShows.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            listOf()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultTvShows
    }

    override fun getFilmCredits(id: Int): LiveData<ApiResponse<List<CastResponse>>> {
        EspressoIdlingResource.increment()

        val resultCredits = MutableLiveData<ApiResponse<List<CastResponse>>>()
        apiService.getTvShowCredits(id, API_KEY)
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

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}