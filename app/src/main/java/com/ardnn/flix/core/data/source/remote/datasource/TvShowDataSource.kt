package com.ardnn.flix.core.data.source.remote.datasource

import android.util.Log
import com.ardnn.flix.core.data.source.remote.ApiConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.core.data.source.remote.response.TvShowResponse
import com.ardnn.flix.core.data.source.remote.service.TvShowApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TvShowDataSource(
    private val apiService: TvShowApiService
) : DetailInterface<TvShowDetailResponse>, FilmsInterface<TvShowResponse> {

    override fun getDetail(id: Int): Flow<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTvShowDetails(id, ApiConfig.API_KEY)
                emit(ApiResponse.Success(response))

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    // get airing today tv shows
    override fun getFirstSectionFilms(page: Int): Flow<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getAiringTodayTvShows(ApiConfig.API_KEY, page)
                val dataArray = response.tvShows
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    // get on the air tv shows
    override fun getSecondSectionFilms(page: Int): Flow<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getOnTheAirTvShows(ApiConfig.API_KEY, page)
                val dataArray = response.tvShows
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    // get popular tv shows
    override fun getThirdSectionFilms(page: Int): Flow<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPopularTvShows(ApiConfig.API_KEY, page)
                val dataArray = response.tvShows
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    // get top rated tv shows
    override fun getFourthSectionFilms(page: Int): Flow<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTopRatedTvShows(ApiConfig.API_KEY, page)
                val dataArray = response.tvShows
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getFilmCredits(id: Int): Flow<ApiResponse<List<CastResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTvShowCredits(id, ApiConfig.API_KEY)
                val dataArray = response.cast
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

}