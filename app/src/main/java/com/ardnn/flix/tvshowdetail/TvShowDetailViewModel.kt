package com.ardnn.flix.tvshowdetail

import androidx.lifecycle.*
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.domain.tvshowdetail.usecase.TvShowDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val tvShowDetailUseCase: TvShowDetailUseCase
) : ViewModel() {

    private val tvShowId = MutableLiveData<Int>()

    var tvShow: LiveData<Resource<TvShowDetail>> =
        Transformations.switchMap(tvShowId) {
            tvShowDetailUseCase.getTvShowDetail(it).asLiveData()
        }

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    fun setTvShowId(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
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