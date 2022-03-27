package com.ardnn.flix.core.di

import com.ardnn.flix.core.BuildConfig
import com.ardnn.flix.core.data.source.remote.service.MovieApiService
import com.ardnn.flix.core.data.source.remote.service.PersonApiService
import com.ardnn.flix.core.data.source.remote.service.TvShowApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
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
        val hostname = BuildConfig.HOSTNAME_TMDB
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()
        
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_TMDB)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}