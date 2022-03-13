package com.ardnn.flix.core.data.source.remote.datasource

import android.util.Log
import com.ardnn.flix.core.data.source.remote.ApiConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.PersonResponse
import com.ardnn.flix.core.data.source.remote.service.PersonApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PersonDataSource(
    private val apiService: PersonApiService
) : DetailInterface<PersonResponse> {

    override fun getDetail(id: Int): Flow<ApiResponse<PersonResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPersonDetail(id, ApiConfig.API_KEY)
                emit(ApiResponse.Success(response))

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }
}