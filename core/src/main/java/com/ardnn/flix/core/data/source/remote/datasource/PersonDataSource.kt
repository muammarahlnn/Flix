package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.BuildConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.PersonResponse
import com.ardnn.flix.core.data.source.remote.service.PersonApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class PersonDataSource @Inject constructor(
    private val apiService: PersonApiService
) : ApiKeyInterface, DetailInterface<PersonResponse> {

    override val apiKey: String
        get() = BuildConfig.API_KEY

    override fun getDetail(id: Int): Flow<ApiResponse<PersonResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPersonDetail(id, apiKey)
                emit(ApiResponse.Success(response))

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e(e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }
}