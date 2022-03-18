package com.ardnn.flix.core.domain.movies.repository

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.movies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMovies(page: Int, section: Int, filter: String): Flow<Resource<List<Movie>>>
}