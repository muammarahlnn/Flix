package com.ardnn.flix.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ardnn.flix.core.data.FlixRepositoryImpl
import com.ardnn.flix.core.util.DataDummy
import com.ardnn.flix.core.util.PagedTestDataSources
import com.ardnn.flix.core.util.SortUtils
import com.ardnn.flix.core.vo.Resource
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
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepositoryImpl

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    private lateinit var viewModel: MoviesViewModel
    private lateinit var dataDummy: DataDummy
    private val page = 1

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(flixRepository)

        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)
    }

    @Test
    fun `getMovies should be success`() {
        val movies = PagedTestDataSources.snapshot(dataDummy.generateDummyMovies())
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(movies)

        `when`(flixRepository.getMovies(page, 0, SortUtils.DEFAULT))
            .thenReturn(expected)

        viewModel.getMovies(page, SortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovies(page, SortUtils.DEFAULT).value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun `getMovies should be empty`() {
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.success(PagedTestDataSources.snapshot())

        `when`(flixRepository.getMovies(page, 0, SortUtils.DEFAULT))
            .thenReturn(expected)

        viewModel.getMovies(page, SortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualValueDataSize = viewModel.getMovies(page, SortUtils.DEFAULT).value?.data?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    @Test
    fun `getMovies should be error`() {
        val expectedMessage = "Something went wrong!"
        val expected = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        expected.value = Resource.error(expectedMessage, null)

        `when`(flixRepository.getMovies(page, 0, SortUtils.DEFAULT))
            .thenReturn(expected)

        viewModel.getMovies(page, SortUtils.DEFAULT).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getMovies(page, SortUtils.DEFAULT).value?.message
        assertEquals(expectedMessage, actualMessage)
    }
}