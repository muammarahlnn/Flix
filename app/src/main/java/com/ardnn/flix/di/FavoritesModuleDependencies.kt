package com.ardnn.flix.di

import com.ardnn.flix.core.domain.favorites.usecase.FavoritesUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoritesModuleDependencies {

    fun favoritesUseCase(): FavoritesUseCase
}