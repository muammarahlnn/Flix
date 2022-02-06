package com.ardnn.flix.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.vo.Resource

class TvShowsViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getOnTheAirTvShows(page: Int): LiveData<Resource<List<TvShowEntity>>> =
        flixRepository.getOnTheAirTvShows(page)

    fun getTopRatedTvShows(page: Int): LiveData<Resource<List<TvShowEntity>>> =
        flixRepository.getTopRatedTvShows(page)

    fun setSection(section: Int) {
        this.section = section
    }
}