package com.ardnn.flix.core.data.source.remote.datasource

import android.annotation.SuppressLint
import android.util.Log
import com.ardnn.flix.core.data.source.remote.ApiConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.core.data.source.remote.response.MovieResponse
import com.ardnn.flix.core.data.source.remote.service.MovieApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MovieDataSource(
    private val apiService: MovieApiService
) : DetailInterface<MovieDetailResponse>, FilmsInterface<MovieResponse> {

    // method to get movie detail
    @SuppressLint("CheckResult")
    override fun getDetail(id: Int): Flowable<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<MovieDetailResponse>>()
        apiService.getMovieDetails(id, ApiConfig.API_KEY)
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

    // method to get now playing movies
    @SuppressLint("CheckResult")
    override fun getFirstSectionFilms(page: Int): Flowable<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()
        apiService.getNowPlayingMovies(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.movies as List<MovieResponse>
                Log.d("RemoteDataSource", "movies empty -> ${response.movies.isEmpty()}")
                if (dataArray.isNotEmpty()) {
                    Log.d("RemoteDataSource", "movies success")
                    resultData.onNext(ApiResponse.Success(dataArray))
                } else {
                    Log.d("RemoteDataSource", "movies empty")
                    resultData.onNext(ApiResponse.Empty)
                }
//                resultData.onNext(
//                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
//                    else ApiResponse.Empty
//                )
                EspressoIdlingResource.decrement()
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())

                EspressoIdlingResource.decrement()
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    // method to get upcoming movies
    @SuppressLint("CheckResult")
    override fun getSecondSectionFilms(page: Int): Flowable<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()
        apiService.getUpcomingMovies(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.movies ?: listOf()
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

    // method to get popular movies
    @SuppressLint("CheckResult")
    override fun getThirdSectionFilms(page: Int): Flowable<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()
        apiService.getPopularMovies(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.movies ?: listOf()
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

    // method to get top rated movies
    @SuppressLint("CheckResult")
    override fun getFourthSectionFilms(page: Int): Flowable<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()
        apiService.getTopRatedMovies(ApiConfig.API_KEY, page)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.movies ?: listOf()
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
        apiService.getMovieCredits(id, ApiConfig.API_KEY)
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