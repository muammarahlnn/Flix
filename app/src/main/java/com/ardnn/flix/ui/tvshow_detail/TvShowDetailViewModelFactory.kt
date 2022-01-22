package com.ardnn.flix.ui.tvshow_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TvShowDetailViewModelFactory(
    private val tvShowId: Int
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowDetailViewModel::class.java)) {
            return TvShowDetailViewModel(tvShowId) as T
        }
        return super.create(modelClass)
    }
}