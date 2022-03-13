package com.ardnn.flix.core.data.source.remote.service

import com.ardnn.flix.core.data.source.remote.response.PersonResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApiService {
    @GET("3/person/{person_id}")
    fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Flowable<PersonResponse>
}