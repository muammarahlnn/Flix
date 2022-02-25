package com.ardnn.flix.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.flix.ui.film.FilmFragment

class FilmPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FilmFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return HomeActivity.TAB_TITLES.size
    }
}