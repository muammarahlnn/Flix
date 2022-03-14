package com.ardnn.flix.core.di

import com.ardnn.flix.core.data.FlixRepositoryImpl
import com.ardnn.flix.core.domain.repository.FlixRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(flixRepository: FlixRepositoryImpl): FlixRepository
}