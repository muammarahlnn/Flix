package com.ardnn.flix.di

import com.ardnn.flix.core.domain.usecase.FlixInteractor
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideFlixUseCase(flixInteractor: FlixInteractor): FlixUseCase
}