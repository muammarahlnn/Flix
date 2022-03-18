package com.ardnn.flix.core.domain.movies.usecase

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.movies.model.Movie
import com.ardnn.flix.core.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesInteractor @Inject constructor(
    private val moviesRepository: MoviesRepository
) : MoviesUseCase {

    override fun getMovies(page: Int, section: Int, filter: String): Flow<Resource<List<Movie>>> =
        moviesRepository.getMovies(page, section, filter)
}