package com.ardnn.flix.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import com.ardnn.flix.core.vo.Resource

class TvShowsViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    private var section = 0

    private var sortArr = arrayOf("", "", "", "")
    private val _tvShowsSort = MutableLiveData(sortArr)
    val tvShowsSort: LiveData<Array<String>> = _tvShowsSort

    fun getSectionWithTvShows(page: Int, filter: String): LiveData<Resource<List<TvShow>>> =
        LiveDataReactiveStreams.fromPublisher(flixUseCase.getSectionWithTvShows(page, section, filter))

    fun setSection(section: Int) {
        this.section = section
    }

    fun setTvShowsSort(filter: String) {
        _tvShowsSort.value?.let {
            it[section] = filter
        }
    }
}