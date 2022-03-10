package com.ardnn.flix.core.data.source.remote.datasource

import androidx.lifecycle.LiveData
import com.ardnn.flix.core.data.source.remote.ApiResponse

interface DetailInterface<T> {
    fun getDetail(id: Int): LiveData<ApiResponse<T>>
}