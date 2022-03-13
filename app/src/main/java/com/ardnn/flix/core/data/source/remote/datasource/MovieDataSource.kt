package com.ardnn.flix.core.data.source.remote.datasource

import android.util.Log
import com.ardnn.flix.core.data.source.remote.ApiConfig
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

class MovieDataSource(
    private val apiService: MovieApiService
) : DetailInterface<MovieDetailResponse>, FilmsInterface<MovieResponse> {

    // method to get movie detail
    override fun getDetail(id: Int): Flow<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getMovieDetails(id, ApiConfig.API_KEY)
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
                val response = apiService.getNowPlayingMovies(ApiConfig.API_KEY, page)
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
                val response = apiService.getUpcomingMovies(ApiConfig.API_KEY, page)
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
                val response = apiService.getPopularMovies(ApiConfig.API_KEY, page)
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
                val response = apiService.getTopRatedMovies(ApiConfig.API_KEY, page)
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
                val response = apiService.getMovieCredits(id, ApiConfig.API_KEY)
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