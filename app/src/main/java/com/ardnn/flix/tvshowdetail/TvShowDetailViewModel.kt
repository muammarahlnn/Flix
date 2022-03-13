package com.ardnn.flix.tvshowdetail

import androidx.lifecycle.*
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import javax.inject.Inject

class TvShowDetailViewModel @Inject constructor(
    private val flixUseCase: FlixUseCase
) : ViewModel() {

    val tvShowId = MutableLiveData<Int>()

    var tvShow: LiveData<Resource<TvShow>> =
        Transformations.switchMap(tvShowId) {
            flixUseCase.getTvShowWithGenres(it).asLiveData()
        }

    var tvShowCredits: LiveData<ApiResponse<List<CastResponse>>> =
        Transformations.switchMap(tvShowId) {
            flixUseCase.getTvShowCredits(it).asLiveData()
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