package com.ardnn.flix.core.data.source.remote.datasource

import android.annotation.SuppressLint
import android.util.Log
import com.ardnn.flix.core.data.source.remote.ApiConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.PersonResponse
import com.ardnn.flix.core.data.source.remote.service.PersonApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PersonDataSource(
    private val apiService: PersonApiService
) : DetailInterface<PersonResponse> {

    @SuppressLint("CheckResult")
    override fun getDetail(id: Int): Flowable<ApiResponse<PersonResponse>> {
        EspressoIdlingResource.increment()

        val resultData = PublishSubject.create<ApiResponse<PersonResponse>>()
        apiService.getPersonDetail(id, ApiConfig.API_KEY)
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
}