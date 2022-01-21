package com.ardnn.flix.api.config

import com.ardnn.flix.api.Const
import com.ardnn.flix.api.callback.TvShowDetailCallback
import com.ardnn.flix.api.callback.TvShowsCallback
import com.ardnn.flix.api.response.TvShow
import com.ardnn.flix.api.response.TvShowDetailResponse
import com.ardnn.flix.api.response.TvShowsResponse
import com.ardnn.flix.api.service.TvShowApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowApiConfig {
    private val TV_SHOW_SERVICE: TvShowApiService =
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL_TV_SHOW)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowApiService::class.java)


    // method to get tv show detail
    fun getTvShowDetail(tvShowId: Int, callback: TvShowDetailCallback) {
        TV_SHOW_SERVICE.getTvShowDetails(tvShowId, Const.API_KEY)
            .enqueue(object : Callback<TvShowDetailResponse> {
                override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            callback.onSuccess(response.body() as TvShowDetailResponse)
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                }
            })
    }

    fun getAiringTodayTvShows(page: Int, callback: TvShowsCallback) {
        TV_SHOW_SERVICE.getAiringTodayTvShows(Const.API_KEY, page)
            .enqueue(object : Callback<TvShowsResponse> {
                override fun onResponse(
                    call: Call<TvShowsResponse>,
                    response: Response<TvShowsResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()?.tvShows != null) {
                                callback.onSuccess(response.body()?.tvShows as List<TvShow>)
                            } else {
                                callback.onFailure("response.body().tvShows is null")
                            }
                        } else {
                            callback.onFailure("response.body() is null")
                        }
                    } else {
                        callback.onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                    callback.onFailure("onFailure: ${t.localizedMessage}")
                }
            })
    }
}