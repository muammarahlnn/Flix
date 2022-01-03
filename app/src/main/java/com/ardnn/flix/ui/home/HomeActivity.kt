package com.ardnn.flix.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.ardnn.flix.R
import com.ardnn.flix.databinding.ActivityHomeBinding
import com.ardnn.flix.utils.Helper
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.movies,
            R.string.tv_shows
        )
    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set pager
        val filmPagerAdapter = FilmPagerAdapter(this)
        binding.vp2Film.adapter = filmPagerAdapter

        // set tab layout
        TabLayoutMediator(binding.tlFilm, binding.vp2Film) { tab, pos ->
            tab.text = getString(TAB_TITLES[pos])
        }.attach()
        Helper.equalingEachTabWidth(binding.tlFilm)
    }
}