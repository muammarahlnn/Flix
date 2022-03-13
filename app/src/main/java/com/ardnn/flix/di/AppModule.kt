package com.ardnn.flix.di

import com.ardnn.flix.core.domain.usecase.FlixInteractor
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideFlixUseCase(flixInteractor: FlixInteractor): FlixUseCase

}