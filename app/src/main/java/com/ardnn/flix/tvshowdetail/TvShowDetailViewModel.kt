package com.ardnn.flix.tvshowdetail

import androidx.lifecycle.*
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.domain.tvshowdetail.usecase.TvShowDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val tvShowDetailUseCase: TvShowDetailUseCase
) : ViewModel() {

    private val tvShowId = MutableLiveData<Int>()

    fun setTvShowId(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    var tvShow: LiveData<Resource<TvShowDetail>> =
        Transformations.switchMap(tvShowId) {
            tvShowDetailUseCase.getTvShowDetail(it).asLiveData()
        }

    var tvShowCasts: LiveData<Resource<List<Cast>>> =
        Transformations.switchMap(tvShowId) {
            tvShowDetailUseCase.getTvShowCasts(it).asLiveData()
        }

    fun setIsFavorite() {
        val tvShowResource = tvShow.value
        if (tvShowResource != null) {
            val tvShow = tvShowResource.data
            if (tvShow != null) {
                val newState = !tvShow.isFavorite
                tvShowDetailUseCase.setIsFavoriteTvShow(tvShow, newState)
            }
        }
    }

}