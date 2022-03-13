package com.ardnn.flix.core.di

import com.ardnn.flix.core.data.source.remote.service.MovieApiService
import com.ardnn.flix.core.data.source.remote.service.PersonApiService
import com.ardnn.flix.core.data.source.remote.service.TvShowApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideMovieApiService(client: OkHttpClient): MovieApiService =
        buildRetrofit(client).create(MovieApiService::class.java)

    @Provides
    fun provideTvShowApiService(client: OkHttpClient): TvShowApiService =
        buildRetrofit(client).create(TvShowApiService::class.java)

    @Provides
    fun providePersonApiService(client: OkHttpClient): PersonApiService =
        buildRetrofit(client).create(PersonApiService::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}