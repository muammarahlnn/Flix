package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import io.reactivex.Flowable

interface FilmsInterface<T> {

    fun getFirstSectionFilms(page: Int): Flowable<ApiResponse<List<T>>>

    fun getSecondSectionFilms(page: Int): Flowable<ApiResponse<List<T>>>

    fun getThirdSectionFilms(page: Int): Flowable<ApiResponse<List<T>>>

    fun getFourthSectionFilms(page: Int): Flowable<ApiResponse<List<T>>>

    fun getFilmCredits(id: Int): Flowable<ApiResponse<List<CastResponse>>>
}