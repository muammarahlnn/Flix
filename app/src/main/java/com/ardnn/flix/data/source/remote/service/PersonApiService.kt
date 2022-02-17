package com.ardnn.flix.data.source.remote.service

import com.ardnn.flix.data.source.remote.response.PersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApiService {
    @GET("{person_id}")
    fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<PersonResponse>
}