package com.ardnn.flix.di

import com.ardnn.flix.core.domain.usecase.FlixInteractor
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideFlixUseCase(flixInteractor: FlixInteractor): FlixUseCase
}