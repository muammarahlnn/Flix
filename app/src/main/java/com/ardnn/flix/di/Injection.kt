package com.ardnn.flix.di

import android.content.Context
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.LocalDataSource
import com.ardnn.flix.data.source.local.room.FlixDatabase
import com.ardnn.flix.data.source.remote.ApiConfig
import com.ardnn.flix.data.source.remote.datasource.MovieDataSource
import com.ardnn.flix.data.source.remote.datasource.PersonDataSource
import com.ardnn.flix.data.source.remote.datasource.RemoteDataSource
import com.ardnn.flix.data.source.remote.datasource.TvShowDataSource
import com.ardnn.flix.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): FlixRepository {
        val database = FlixDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(
            MovieDataSource(ApiConfig.getMovieApiService()),
            TvShowDataSource(ApiConfig.getTvShowApiService()),
            PersonDataSource(ApiConfig.getPersonApiService())
        )
        val localDataSource = LocalDataSource.getInstance(
            database.movieDao(),
            database.tvShowDao(),
            database.sectionDao(),
            database.genreDao()
        )
        val appExecutors = AppExecutors()

        return FlixRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}