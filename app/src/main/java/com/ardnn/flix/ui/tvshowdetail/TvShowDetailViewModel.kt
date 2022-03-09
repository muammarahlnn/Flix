package com.ardnn.flix.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.TvShowWithGenres
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.CastResponse
import com.ardnn.flix.vo.Resource

class TvShowDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val tvShowId = MutableLiveData<Int>()

    var tvShow: LiveData<Resource<TvShowWithGenres>> =
        Transformations.switchMap(tvShowId) {
            flixRepository.getTvShowWithGenres(it)
        }

    var tvShowCredits: LiveData<ApiResponse<List<CastResponse>>> =
        Transformations.switchMap(tvShowId) { mTvShowId ->
            flixRepository.getTvShowCredits(mTvShowId)
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
        val tvShowDetailResource = tvShow.value
        if (tvShowDetailResource != null) {
            val tvShowDetailEntity = tvShowDetailResource.data
            if (tvShowDetailEntity != null) {
                val newState = !tvShowDetailEntity.tvShow.isFavorite
                flixRepository.setIsFavoriteTvShow(tvShowDetailEntity.tvShow, newState)
            }
        }
    }

}