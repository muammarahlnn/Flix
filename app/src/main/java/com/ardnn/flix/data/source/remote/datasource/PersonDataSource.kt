package com.ardnn.flix.data.source.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ardnn.flix.BuildConfig
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.PersonResponse
import com.ardnn.flix.data.source.remote.service.PersonApiService
import com.ardnn.flix.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDataSource(
    private val apiService: PersonApiService
) : DetailInterface<PersonResponse> {

    override fun getDetail(id: Int): LiveData<ApiResponse<PersonResponse>> {
        EspressoIdlingResource.increment()

        val resultPersonDetail = MutableLiveData<ApiResponse<PersonResponse>>()
        apiService.getPersonDetail(id, API_KEY)
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
                                    PersonResponse()
                                )
                            )
                            EspressoIdlingResource.decrement()
                        }
                    } else {
                        resultPersonDetail.postValue(
                            ApiResponse.error(
                                response.message(),
                                PersonResponse()
                            )
                        )
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<PersonResponse>, t: Throwable) {
                    resultPersonDetail.postValue(
                        ApiResponse.error(
                            "onFailure: ${t.localizedMessage}",
                            PersonResponse()
                        )
                    )
                    EspressoIdlingResource.decrement()
                }

            })

        return resultPersonDetail
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}