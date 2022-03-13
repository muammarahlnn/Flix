package com.ardnn.flix.core.di

import com.ardnn.flix.core.data.FlixRepositoryImpl
import com.ardnn.flix.core.domain.repository.FlixRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(flixRepository: FlixRepositoryImpl): FlixRepository

}