package com.ardnn.flix.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MoviesPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = MoviesFragment()
        fragment.arguments = Bundle().apply {
            putInt(MoviesFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        // [now playing, upcoming, popular, top rated]
        return 4
    }
}