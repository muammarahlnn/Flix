package com.ardnn.flix.di

import android.content.Context
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.LocalDataSource
import com.ardnn.flix.data.source.local.room.FlixDatabase
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FlixRepository {
        val database = FlixDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.flixDao())
        val appExecutors = AppExecutors()

        return FlixRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}