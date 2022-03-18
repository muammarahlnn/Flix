package com.ardnn.flix.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.tvshows.model.TvShow
import com.ardnn.flix.core.domain.tvshows.usecase.TvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val tvShowsUseCase: TvShowsUseCase
) : ViewModel() {

    private var section = 0

    private var sortArr = arrayOf("", "", "", "")
    private val _tvShowsSort = MutableLiveData(sortArr)
    val tvShowsSort: LiveData<Array<String>> = _tvShowsSort

    fun getTvShows(page: Int, filter: String): LiveData<Resource<List<TvShow>>> =
        tvShowsUseCase.getTvShows(page, section, filter).asLiveData()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setTvShowsSort(filter: String) {
        _tvShowsSort.value?.let {
            it[section] = filter
        }
    }
}