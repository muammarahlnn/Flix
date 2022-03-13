package com.ardnn.flix.core.data.source.remote.datasource

import android.util.Log
import com.ardnn.flix.BuildConfig
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.core.data.source.remote.response.MovieResponse
import com.ardnn.flix.core.data.source.remote.service.MovieApiService
import com.ardnn.flix.core.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val apiService: MovieApiService
) : ApiKeyInterface, DetailInterface<MovieDetailResponse>, FilmsInterface<MovieResponse> {

    override val apiKey: String
        get() = BuildConfig.API_KEY

    // method to get movie detail
    override fun getDetail(id: Int): Flow<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getMovieDetails(id, apiKey)
                emit(ApiResponse.Success(response))

                EspressoIdlingResource.decrement()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

                EspressoIdlingResource.decrement()
            }
        }.flowOn(Dispatchers.IO)
    }

    // method to get now playing movies
    override fun getFirstSectionFilms(page: Int): Flow<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getNowPlayingMovies(apiKey, page)
                val dataArray = response.movies
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                }  else {
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

    // method to get upcoming movies
    override fun getSecondSectionFilms(page: Int): Flow<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getUpcomingMovies(apiKey, page)
                val dataArray = response.movies
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                }  else {
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

    // method to get popular movies
    override fun getThirdSectionFilms(page: Int): Flow<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getPopularMovies(apiKey, page)
                val dataArray = response.movies
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                }  else {
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

    // method to get top rated movies
    override fun getFourthSectionFilms(page: Int): Flow<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getTopRatedMovies(apiKey, page)
                val dataArray = response.movies
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                }  else {
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
                val response = apiService.getMovieCredits(id, apiKey)
                val dataArray = response.cast
                if (!dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                }  else {
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