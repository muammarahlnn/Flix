package com.ardnn.flix.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ardnn.flix.data.source.local.LocalDataSource
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.data.source.remote.response.MovieResponse
import com.ardnn.flix.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.data.source.remote.response.TvShowResponse
import com.ardnn.flix.utils.*
import com.ardnn.flix.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FlixRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val flixRepository = FakeFlixRepository(remote, local, appExecutors)

    private lateinit var dataDummy: DataDummy

    private var movieId: Int = 0
    private lateinit var dummyMoviesResponse: List<MovieResponse>
    private lateinit var dummyMovieDetailResponse: MovieDetailResponse

    private var tvShowId: Int = 0
    private lateinit var dummyTvShowsResponse: List<TvShowResponse>
    private lateinit var dummyTvShowDetailResponse: TvShowDetailResponse

    private val page = 1

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)

        dummyMoviesResponse = dataDummy.generateRemoteDummyMovies()
        movieId = dummyMoviesResponse[0].id
        dummyMovieDetailResponse = dataDummy.generateRemoteDummyMovieDetail()

        dummyTvShowsResponse = dataDummy.generateRemoteDummyTvShows()
        tvShowId = dummyTvShowsResponse[0].id
        dummyTvShowDetailResponse = dataDummy.generateRemoteDummyTvShowDetail()
    }

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies(0, SortUtils.DEFAULT))
            .thenReturn(dataSourceFactory)
        flixRepository.getMovies(page, 0, SortUtils.DEFAULT)

        val moviesEntity = Resource.success(PagedListUtil.mockPagedList(dataDummy.generateDummyMovies()))
        verify(local).getMovies(0, SortUtils.DEFAULT)

        assertNotNull(moviesEntity.data)
        assertEquals(dummyMoviesResponse.size.toLong(), moviesEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieDetailEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        flixRepository.getFavoriteMovies()

        val favoriteMoviesEntity = Resource.success(PagedListUtil.mockPagedList(dataDummy.generateDummyMovies()))
        verify(local).getFavoriteMovies()

        assertNotNull(favoriteMoviesEntity.data)
        assertEquals(dummyMoviesResponse.size.toLong(), favoriteMoviesEntity.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyEntity = MutableLiveData<MovieDetailEntity>()
        dummyEntity.value = dataDummy.generateDummyMovieDetail()
        `when`(local.getMovieDetail(movieId)).thenReturn(dummyEntity)

        val movieDetailEntity = LiveDataTestUtil.getValue(flixRepository.getMovieDetail(movieId))
        verify(local).getMovieDetail(movieId)

        assertNotNull(movieDetailEntity.data)
        assertNotNull(movieDetailEntity.data?.id)
        assertEquals(dummyMovieDetailResponse.id, movieDetailEntity.data?.id)
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShows(0, SortUtils.DEFAULT))
            .thenReturn(dataSourceFactory)
        flixRepository.getTvShows(page, 0, SortUtils.DEFAULT)

        val tvShowsEntity = Resource.success(PagedListUtil.mockPagedList(dataDummy.generateRemoteDummyTvShows()))
        verify(local).getTvShows(0, SortUtils.DEFAULT)

        assertNotNull(tvShowsEntity.data)
        assertEquals(dummyTvShowsResponse.size.toLong(), tvShowsEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowDetailEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        flixRepository.getFavoriteTvShows()

        val favoriteTvShowsEntity = Resource.success(PagedListUtil.mockPagedList(dataDummy.generateDummyTvShows()))
        verify(local).getFavoriteTvShows()

        assertNotNull(favoriteTvShowsEntity.data)
        assertEquals(dummyTvShowsResponse.size.toLong(), favoriteTvShowsEntity.data?.size?.toLong())
    }

    @Test
    fun getTvShowDetail() {
        val dummyEntity = MutableLiveData<TvShowDetailEntity>()
        dummyEntity.value = dataDummy.generateDummyTvShowDetail()
        `when`(local.getTvShowDetail(tvShowId)).thenReturn(dummyEntity)

        val tvShowDetailEntity = LiveDataTestUtil.getValue(flixRepository.getTvShowDetail(tvShowId))
        verify(local).getTvShowDetail(tvShowId)

        assertNotNull(tvShowDetailEntity.data)
        assertNotNull(tvShowDetailEntity.data?.id)
        assertEquals(dummyTvShowDetailResponse.id, tvShowDetailEntity.data?.id)
    }
}