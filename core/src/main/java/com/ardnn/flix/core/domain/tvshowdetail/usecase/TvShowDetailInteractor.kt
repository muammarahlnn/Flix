package com.ardnn.flix.core.domain.tvshowdetail.usecase

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.domain.tvshowdetail.repository.TvShowDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowDetailInteractor @Inject constructor(
    private val tvShowDetailRepository: TvShowDetailRepository
) : TvShowDetailUseCase {

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetail>> =
        tvShowDetailRepository.getTvShowDetail(tvShowId)

    override fun getTvShowCasts(tvShowId: Int): Flow<Resource<List<Cast>>> =
        tvShowDetailRepository.getTvShowCasts(tvShowId)

    override fun setIsFavoriteTvShow(tvShow: TvShowDetail, state: Boolean) {
        tvShowDetailRepository.setIsFavoriteTvShow(tvShow, state)
    }
}