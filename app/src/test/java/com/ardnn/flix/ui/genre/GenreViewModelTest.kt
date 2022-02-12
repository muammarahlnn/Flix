package com.ardnn.flix.ui.genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.GenreWithMovieDetails
import com.ardnn.flix.data.source.local.entity.relation.GenreWithTvShowDetails
import com.ardnn.flix.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class GenreViewModelTest {

    private lateinit var viewModel: GenreViewModel
    private lateinit var dataDummy: DataDummy
    private var genreId = 21

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepository

    @Mock
    private lateinit var genreMoviesObserver: Observer<GenreWithMovieDetails>

    @Mock
    private lateinit var genreTvShowsObserver: Observer<GenreWithTvShowDetails>

    @Before
    fun setUp() {
        viewModel = GenreViewModel(flixRepository)
        viewModel.setGenreId(genreId)

        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)
    }

    @Test
    fun `getGenreWithMovies should be success`() {
        val expected = MutableLiveData<GenreWithMovieDetails>()
        expected.value = dataDummy.generateDummyGenreWithMovies()

        `when`(flixRepository.getGenreWithMovies(genreId))
            .thenReturn(expected)

        viewModel.getGenreWithMovies().observeForever(genreMoviesObserver)
        verify(genreMoviesObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getGenreWithMovies().value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `getGenreWithMovies should be success but data is empty`() {
        val expected = MutableLiveData<GenreWithMovieDetails>()
        expected.value = dataDummy.generateDummyGenreWithMovies(true)

        `when`(flixRepository.getGenreWithMovies(genreId))
            .thenReturn(expected)

        viewModel.getGenreWithMovies().observeForever(genreMoviesObserver)
        verify(genreMoviesObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getGenreWithMovies().value?.movieDetails?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    @Test
    fun `getGenreWithTvShows should be success`() {
        val expected = MutableLiveData<GenreWithTvShowDetails>()
        expected.value = dataDummy.generateDummyGenreWithTvShows()

        `when`(flixRepository.getGenreWithTvShows(genreId))
            .thenReturn(expected)

        viewModel.getGenreWithTvShows().observeForever(genreTvShowsObserver)
        verify(genreTvShowsObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getGenreWithTvShows().value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `getGenreWithTvShows should be success but data is empty`() {
        val expected = MutableLiveData<GenreWithTvShowDetails>()
        expected.value = dataDummy.generateDummyGenreWithTvShows(true)

        `when`(flixRepository.getGenreWithTvShows(genreId))
            .thenReturn(expected)

        viewModel.getGenreWithTvShows().observeForever(genreTvShowsObserver)
        verify(genreTvShowsObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getGenreWithTvShows().value?.tvShowDetails?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }
}