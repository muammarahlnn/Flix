package com.ardnn.flix.data.source.remote

import com.ardnn.flix.BuildConfig
import com.ardnn.flix.data.source.remote.service.MovieApiService
import com.ardnn.flix.data.source.remote.service.PersonApiService
import com.ardnn.flix.data.source.remote.service.TvShowApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    const val API_KEY = BuildConfig.API_KEY
    private const val BASE_URL = "https://api.themoviedb.org/"

    fun getMovieApiService(): MovieApiService =
        buildRetrofit().create(MovieApiService::class.java)

    fun getTvShowApiService(): TvShowApiService =
        buildRetrofit().create(TvShowApiService::class.java)

    fun getPersonApiService(): PersonApiService =
        buildRetrofit().create(PersonApiService::class.java)

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}