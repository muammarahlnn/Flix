package com.ardnn.flix.core.data.source.remote.datasource

import androidx.lifecycle.LiveData
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse

interface FilmsInterface<T> {

    fun getFirstSectionFilms(page: Int): LiveData<ApiResponse<List<T>>>

    fun getSecondSectionFilms(page: Int): LiveData<ApiResponse<List<T>>>

    fun getThirdSectionFilms(page: Int): LiveData<ApiResponse<List<T>>>

    fun getFourthSectionFilms(page: Int): LiveData<ApiResponse<List<T>>>

    fun getFilmCredits(id: Int): LiveData<ApiResponse<List<CastResponse>>>
}