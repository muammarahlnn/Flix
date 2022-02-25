package com.ardnn.flix.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.data.source.remote.response.MovieResponse
import com.ardnn.flix.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.data.source.remote.response.TvShowResponse
import com.ardnn.flix.utils.DataDummy
import com.ardnn.flix.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FlixRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val flixRepository = FakeFlixRepository(remote)

    private var movieId: Int = 0
    private lateinit var dummyMoviesResponse: List<MovieResponse>
    private lateinit var dummyMovieDetailResponse: MovieDetailResponse

    private var tvShowId: Int = 0
    private lateinit var dummyTvShowsResponse: List<TvShowResponse>
    private lateinit var dummyTvShowDetailResponse: TvShowDetailResponse

    private val page = 1 // default page to fetch movies and tv shows

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        val dataDummy = DataDummy(context)

        dummyMoviesResponse = dataDummy.generateRemoteDummyMovies()
        movieId = dummyMoviesResponse[0].id
        dummyMovieDetailResponse = dataDummy.generateRemoteDummyMovieDetail()

        dummyTvShowsResponse = dataDummy.generateRemoteDummyTvShows()
        tvShowId = dummyTvShowsResponse[0].id
        dummyTvShowDetailResponse = dataDummy.generateRemoteDummyTvShowDetail()
    }

    @Test
    fun getMovies() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onSuccess(dummyMoviesResponse)
            null
        }.`when`(remote).getNowPlayingMovies(eq(page), any())

        val moviesEntity = LiveDataTestUtil.getValue(flixRepository.getMovies(page))
        verify(remote).getNowPlayingMovies(eq(page), any())

        assertNotNull(moviesEntity)
        assertEquals(dummyMoviesResponse.size.toLong(), moviesEntity.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMovieDetailCallback)
                .onSuccess(dummyMovieDetailResponse)
            null
        }.`when`(remote).getMovieDetail(eq(movieId), any())

        val movieDetailEntity = LiveDataTestUtil.getValue(flixRepository.getMovieDetail(movieId))
        verify(remote).getMovieDetail(eq(movieId), any())

        assertNotNull(movieDetailEntity)
        assertNotNull(movieDetailEntity.id)
        assertEquals(dummyMovieDetailResponse.id, movieDetailEntity.id)
    }

    @Test
    fun getTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvShowsCallback)
                .onSuccess(dummyTvShowsResponse)
            null
        }.`when`(remote).getOnTheAirTvShows(eq(page), any())

        val tvShowsEntity = LiveDataTestUtil.getValue(flixRepository.getTvShows(page))
        verify(remote).getOnTheAirTvShows(eq(page), any())

        assertNotNull(tvShowsEntity)
        assertEquals(dummyTvShowsResponse.size.toLong(), tvShowsEntity.size.toLong())
    }

    @Test
    fun getTvShowDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvShowDetailCallback)
                .onSuccess(dummyTvShowDetailResponse)
            null
        }.`when`(remote).getTvShowDetail(eq(tvShowId), any())

        val tvShowDetailEntity = LiveDataTestUtil.getValue(flixRepository.getTvShowDetail(tvShowId))
        verify(remote).getTvShowDetail(eq(tvShowId), any())

        assertNotNull(tvShowDetailEntity)
        assertNotNull(tvShowDetailEntity.id)
        assertEquals(dummyTvShowDetailResponse.id, tvShowDetailEntity.id)
    }

    @Test
    fun getIsLoadFailure() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onSuccess(dummyMoviesResponse)
            null
        }.`when`(remote).getNowPlayingMovies(eq(page), any())
        LiveDataTestUtil.getValue(flixRepository.getMovies(page))

        val isLoadFailure = LiveDataTestUtil.getValue(flixRepository.getIsLoadFailure())
        assertNotNull(isLoadFailure)
        assertEquals(false, isLoadFailure)
    }

    @Test
    fun getIsLoading() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onSuccess(dummyMoviesResponse)
            null
        }.`when`(remote).getNowPlayingMovies(eq(page), any())
        LiveDataTestUtil.getValue(flixRepository.getMovies(page))

        val isLoading = LiveDataTestUtil.getValue(flixRepository.getIsLoading())
        assertNotNull(isLoading)
        assertEquals(false, isLoading)
    }
}