package com.ardnn.flix.core.data.source.remote.datasource

import android.util.Log
import com.ardnn.flix.BuildConfig
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
import javax.inject.Inject

class TvShowDataSource @Inject constructor(
    private val apiService: TvShowApiService
) : ApiKeyInterface, DetailInterface<TvShowDetailResponse>, FilmsInterface<TvShowResponse> {

    override val apiKey: String
        get() = BuildConfig.API_KEY

    override fun getDetail(id: Int): Flow<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTvShowDetails(id, apiKey)
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
                val response = apiService.getAiringTodayTvShows(apiKey, page)
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
                val response = apiService.getOnTheAirTvShows(apiKey, page)
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
                val response = apiService.getPopularTvShows(apiKey, page)
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
                val response = apiService.getTopRatedTvShows(apiKey, page)
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
                val response = apiService.getTvShowCredits(id, apiKey)
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