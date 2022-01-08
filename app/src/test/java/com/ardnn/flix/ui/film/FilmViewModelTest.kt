package com.ardnn.flix.ui.film

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FilmViewModelTest {

    private lateinit var movieViewModel: FilmViewModel
    private lateinit var tvShowViewModel: FilmViewModel

    @Before
    fun setup() {
        val context = RuntimeEnvironment.getApplication()
        movieViewModel = FilmViewModel(context, 0)
        tvShowViewModel = FilmViewModel(context, 1)
    }

    @Test
    fun getMovies() {
        val movies = movieViewModel.filmList.value
        assertNotNull(movies)
        if (movies != null) {
            assertEquals(19, movies.size)
        }
    }

    @Test
    fun getTvShows() {
        val tvShows = tvShowViewModel.filmList.value
        assertNotNull(tvShows)
        if (tvShows != null) {
            assertEquals(20, tvShows.size)
        }
    }

}