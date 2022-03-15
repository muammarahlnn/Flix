package com.ardnn.flix.favorites

import android.content.Context
import com.ardnn.flix.di.FavoritesModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoritesModuleDependencies::class])
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoritesModuleDependencies: FavoritesModuleDependencies): Builder
        fun build(): FavoritesComponent
    }
}