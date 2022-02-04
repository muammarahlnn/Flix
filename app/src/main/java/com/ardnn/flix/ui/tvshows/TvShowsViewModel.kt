package com.ardnn.flix.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.TvShowEntity

class TvShowsViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getTvShows(page: Int): LiveData<List<TvShowEntity>> =
        flixRepository.getTvShows(page)

    fun getIsLoading(): LiveData<Boolean> =
        flixRepository.getIsLoading()

    fun setSection(section: Int) {
        this.section = section
    }
}