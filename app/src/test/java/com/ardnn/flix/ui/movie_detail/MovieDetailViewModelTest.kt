package com.ardnn.flix.ui.movie_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.MovieDetailWithGenres
import com.ardnn.flix.utils.DataDummy
import com.ardnn.flix.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var dataDummy: DataDummy
    private var movieId = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepository

    @Mock
    private lateinit var movieDetailObserver: Observer<Resource<MovieDetailWithGenres>>

    @Mock
    private lateinit var isSynopsisExtendedObserver: Observer<Boolean>

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)

        val dummyMovies = dataDummy.generateDummyMovies()
        movieId = dummyMovies[0].id

        viewModel = MovieDetailViewModel(flixRepository)
        viewModel.setMovieId(movieId)
    }

    @Test
    fun `getMovieDetail should be success`() {
        val expected = MutableLiveData<Resource<MovieDetailWithGenres>>()
        expected.value = Resource.success(dataDummy.generateDummyMovieDetailWithGenres())

        `when`(flixRepository.getMovieDetailWithGenres(movieId))
            .thenReturn(expected)

        viewModel.movieDetail.observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movieDetail.value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `setIsFavorite should be success trigger movieDetail observer`() {
        val expected = MutableLiveData<Resource<MovieDetailWithGenres>>()
        expected.value = Resource.success(dataDummy.generateDummyMovieDetailWithGenres())

        `when`(flixRepository.getMovieDetailWithGenres(movieId))
            .thenReturn(expected)

        viewModel.setIsFavorite()
        viewModel.movieDetail.observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movieDetail.value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun getIsSynopsisExtended() {
        val isSynopsisExtended = viewModel.isSynopsisExtended.value
        assertNotNull(isSynopsisExtended)
        assertEquals(false, isSynopsisExtended)

        viewModel.isSynopsisExtended.observeForever(isSynopsisExtendedObserver)
        verify(isSynopsisExtendedObserver).onChanged(isSynopsisExtended)
    }
}