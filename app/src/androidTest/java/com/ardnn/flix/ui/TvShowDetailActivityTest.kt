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
import org.hamcrest.core.AllOf

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowDetailActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadTvShowDetail() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvEpisodes)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSeasons)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.rvGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.tvFirstAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLastAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }

    @Test
    fun loadFavoriteTvShowDetail() {
        onView(withId(R.id.navigationTvShows)).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(withId(R.id.btnBack)).perform(click())

        onView(withId(R.id.navigationFavorites)).perform(click())
        onView(AllOf.allOf(withText("TV Shows"), isDescendantOfA(withId(R.id.tabLayout)))).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvEpisodes)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSeasons)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.rvGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.tvFirstAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLastAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }
}