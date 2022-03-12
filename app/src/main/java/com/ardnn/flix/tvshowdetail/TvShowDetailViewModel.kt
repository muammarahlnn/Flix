package com.ardnn.flix.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import com.ardnn.flix.core.vo.Resource

class TvShowDetailViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    val tvShowId = MutableLiveData<Int>()

    var tvShow: LiveData<Resource<TvShow>> =
        Transformations.switchMap(tvShowId) {
            flixUseCase.getTvShowWithGenres(it)
        }

    var tvShowCredits: LiveData<ApiResponse<List<CastResponse>>> =
        Transformations.switchMap(tvShowId) {
            flixUseCase.getTvShowCredits(it)
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
                flixUseCase.setIsFavoriteTvShow(tvShow, newState)
            }
        }
    }

}