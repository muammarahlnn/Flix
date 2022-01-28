package com.ardnn.flix.ui.film

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.FilmEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FilmViewModelTest {

    private lateinit var viewModel: FilmViewModel
    private lateinit var dataDummy: DataDummy

    @get:Rule
    var instantTaskEnvironment = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepository

    @Mock
    private lateinit var moviesObserver: Observer<List<MovieEntity>>

    @Mock
    private lateinit var tvShowsObserver: Observer<List<TvShowEntity>>

    @Mock
    private lateinit var isLoadFailureObserver: Observer<Boolean>

    @Mock
    private lateinit var isLoadingObserver: Observer<Boolean>

    private val page = 1 // default page to fetch movies and tv shows

    @Before
    fun setup() {
        viewModel = FilmViewModel(flixRepository)

        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)
    }

    @Test
    fun getMovies() {
        val dummyMovies = dataDummy.generateDummyMovies()
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovies

        `when`(flixRepository.getMovies(page))
            .thenReturn(movies)

        val moviesEntity = viewModel.getMovies(page).value
        verify(flixRepository).getMovies(page)

        assertNotNull(moviesEntity)
        assertEquals(20, moviesEntity?.size)

        viewModel.getMovies(page).observeForever(moviesObserver)
        verify(moviesObserver).onChanged(dummyMovies)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = dataDummy.generateDummyTvShows()
        val tvShows = MutableLiveData<List<TvShowEntity>>()
        tvShows.value = dummyTvShows

        `when`(flixRepository.getTvShows(page))
            .thenReturn(tvShows)

        val tvShowsEntity = viewModel.getTvShows(page).value
        verify(flixRepository).getTvShows(page)

        assertNotNull(tvShowsEntity)
        assertEquals(20, tvShowsEntity?.size)

        viewModel.getTvShows(page).observeForever(tvShowsObserver)
        verify(tvShowsObserver).onChanged(dummyTvShows)
    }

    @Test
    fun getIsLoadFailure() {
        val dummyIsLoadFailure = MutableLiveData<Boolean>()
        dummyIsLoadFailure.value = false

        `when`(flixRepository.getIsLoadFailure())
            .thenReturn(dummyIsLoadFailure)

        val isLoadFailure = viewModel.getIsLoadFailure().value
        verify(flixRepository).getIsLoadFailure()

        assertNotNull(isLoadFailure)
        assertEquals(false, isLoadFailure)

        viewModel.getIsLoadFailure().observeForever(isLoadFailureObserver)
        verify(isLoadFailureObserver).onChanged(false)
    }

    @Test
    fun getIsLoading() {
        val dummyIsLoading = MutableLiveData<Boolean>()
        dummyIsLoading.value = false

        `when`(flixRepository.getIsLoading())
            .thenReturn(dummyIsLoading)

        val isLoading = viewModel.getIsLoading().value
        verify(flixRepository).getIsLoading()

        assertNotNull(isLoading)
        assertEquals(false, isLoading)

        viewModel.getIsLoading().observeForever(isLoadingObserver)
        verify(isLoadingObserver).onChanged(false)
    }

}