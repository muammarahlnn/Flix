package com.ardnn.flix.core.data.source.remote

import com.ardnn.flix.BuildConfig
import com.ardnn.flix.core.data.source.remote.service.MovieApiService
import com.ardnn.flix.core.data.source.remote.service.PersonApiService
import com.ardnn.flix.core.data.source.remote.service.TvShowApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
            .client(provideOkHttpClient())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
}