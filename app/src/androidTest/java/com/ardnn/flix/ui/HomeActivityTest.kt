package com.ardnn.flix.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ardnn.flix.R
import com.ardnn.flix.ui.home.HomeActivity
import com.ardnn.flix.utils.EspressoIdlingResource
import org.hamcrest.core.AllOf.allOf
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    private val listSize = 20

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadNowPlayingMovies() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadUpcomingMovies() {
        onView(withText("Upcoming")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadPopularMovies() {
        onView(withText("Popular")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadTopRatedMovies() {
        onView(withText("Top Rated")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadAiringTodayTvShows() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadOnTheAirTvShows() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withText("On The Air")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadPopularTvShows() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withText("Popular")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun loadTopRatedTvShows() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withText("Top Rated")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSize))
    }

    @Test
    fun addAndRemoveFavoriteMovie() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.btnBack)).perform(click())

        onView(withId(R.id.navigationFavorites)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.btnBack)).perform(click())
    }

    @Test
    fun addAndRemoveFavoriteTvShow() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.btnBack)).perform(click())

        onView(withId(R.id.navigationFavorites)).perform(click())
        onView(allOf(withText("TV Shows"), isDescendantOfA(withId(R.id.tabLayout)))).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.btnBack)).perform(click())
    }

}