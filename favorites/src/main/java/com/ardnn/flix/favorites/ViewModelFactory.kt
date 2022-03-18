package com.ardnn.flix.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.core.domain.favorites.usecase.FavoritesUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(favoritesUseCase) as T
            }
            else -> throw  Throwable("Unknown ViewModel class ${modelClass.name}")
        }
    }
}