package com.ardnn.flix.core.data.source.remote.datasource

import android.annotation.SuppressLint
import android.util.Log
import com.ardnn.flix.core.data.source.remote.ApiConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowResponse
import com.ardnn.flix.core.data.source.remote.service.TvShowApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TvShowDataSource(
    private val apiService: TvShowApiService
) : DetailInterface<TvShowDetailResponse>, FilmsInterface<TvShowResponse> {

    @SuppressLint("CheckResult")
    override fun getDetail(id: Int): Flowable<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<TvShowDetailResponse>>()
        apiService.getTvShowDetails(id, ApiConfig.API_KEY)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(
                    if (response != null) ApiResponse.Success(response)
                    else ApiResponse.Empty
                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    // get airing today tv shows
    @SuppressLint("CheckResult")
    override fun getFirstSectionFilms(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<TvShowResponse>>>()
        apiService.getAiringTodayTvShows(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.tvShows ?: listOf()
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    // get on the air tv shows
    @SuppressLint("CheckResult")
    override fun getSecondSectionFilms(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<TvShowResponse>>>()
        apiService.getOnTheAirTvShows(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.tvShows ?: listOf()
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    // get popular tv shows
    @SuppressLint("CheckResult")
    override fun getThirdSectionFilms(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<TvShowResponse>>>()
        apiService.getPopularTvShows(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.tvShows ?: listOf()
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    // get top rated tv shows
    @SuppressLint("CheckResult")
    override fun getFourthSectionFilms(page: Int): Flowable<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<TvShowResponse>>>()
        apiService.getTopRatedTvShows(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.tvShows ?: listOf()
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun getFilmCredits(id: Int): Flowable<ApiResponse<List<CastResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<CastResponse>>>()
        apiService.getTvShowCredits(id, ApiConfig.API_KEY)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.cast
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

}