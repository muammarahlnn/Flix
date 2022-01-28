package com.ardnn.flix.ui.home

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.utils.DataDummy
import com.ardnn.flix.utils.EspressoIdlingResource
import com.ardnn.flix.utils.FilmsData
import com.ardnn.flix.utils.Helper
import org.hamcrest.CoreMatchers
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    private var movieId: Int = 0
    private lateinit var dummyMoviesEntity: List<MovieEntity>
    private lateinit var dummyMovieDetailEntity: MovieDetailEntity

    private var tvShowId: Int = 0
    private lateinit var dummyTvShowsEntity: List<TvShowEntity>
    private lateinit var dummyTvShowDetailEntity: TvShowDetailEntity

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataDummy = DataDummy(context)

        dummyMoviesEntity = dataDummy.generateDummyMovies()
        movieId = dummyMoviesEntity[0].id
        dummyMovieDetailEntity = dataDummy.generateDummyMovieDetail()

        dummyTvShowsEntity = dataDummy.generateDummyTvShows()
        tvShowId = dummyTvShowsEntity[0].id
        dummyTvShowDetailEntity = dataDummy.generateDummyTvShowDetail()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rvFilm)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMoviesEntity.size))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rvFilm)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShowsEntity.size))
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(dummyMovieDetailEntity.wallpaperUrl))))

        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(dummyMovieDetailEntity.posterUrl))))

        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText(dummyMovieDetailEntity.title)))

        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText(Helper.convertToDate(dummyMovieDetailEntity.releaseDate))))

        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(withText("${dummyMovieDetailEntity.runtime} mins")))

        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(withText(dummyMovieDetailEntity.rating.toString())))

        val genreList = dummyMovieDetailEntity.genreList as List<GenreEntity>
        onView(withId(R.id.rvGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.rvGenre)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(genreList.size))

        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(withText(dummyMovieDetailEntity.overview)))

        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }

    @Test
    fun loadTvShowDetail() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(dummyTvShowDetailEntity.wallpaperUrl))))

        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(dummyTvShowDetailEntity.posterUrl))))

        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText(dummyTvShowDetailEntity.title)))

        onView(withId(R.id.tvEpisodes)).check(matches(isDisplayed()))
        onView(withId(R.id.tvEpisodes)).check(matches(withText(dummyTvShowDetailEntity.numberOfEpisodes.toString())))

        onView(withId(R.id.tvSeasons)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSeasons)).check(matches(withText(dummyTvShowDetailEntity.numberOfSeasons.toString())))

        val runtime = dummyTvShowDetailEntity.runtimes?.get(0)
        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(withText("$runtime mins")))

        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(withText(dummyTvShowDetailEntity.rating.toString())))

        val genreList = dummyTvShowDetailEntity.genreList as List<GenreEntity>
        onView(withId(R.id.rvGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.rvGenre)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(genreList.size))

        onView(withId(R.id.tvFirstAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvFirstAiring)).check(matches(withText(Helper.convertToDate(dummyTvShowDetailEntity.firstAirDate))))

        onView(withId(R.id.tvLastAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLastAiring)).check(matches(withText(Helper.convertToDate(dummyTvShowDetailEntity.lastAirDate))))

        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(withText(dummyTvShowDetailEntity.overview)))

        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }


}