package com.ardnn.flix.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoritesPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    override fun createFragment(position: Int): Fragment {
        val fragment = FavoritesFragment()
        fragment.arguments = Bundle().apply {
            putInt(FavoritesFragment.ARG_SECTION_NUMBER, position)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}