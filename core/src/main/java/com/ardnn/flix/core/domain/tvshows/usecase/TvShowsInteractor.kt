package com.ardnn.flix.core.domain.tvshows.usecase

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.tvshows.model.TvShow
import com.ardnn.flix.core.domain.tvshows.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowsInteractor @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
): TvShowsUseCase {

    override fun getTvShows(page: Int, section: Int, filter: String): Flow<Resource<List<TvShow>>> =
        tvShowsRepository.getTvShows(page, section, filter)
}