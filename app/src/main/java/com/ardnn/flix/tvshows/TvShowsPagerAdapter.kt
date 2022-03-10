package com.ardnn.flix.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TvShowsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = TvShowsFragment()
        fragment.arguments = Bundle().apply {
            putInt(TvShowsFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        // [airing today, on the air, popular, top rated]
        return 4
    }

}