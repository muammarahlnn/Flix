package com.ardnn.flix.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ardnn.flix.R
import com.ardnn.flix.databinding.ActivityHomeBinding
import com.ardnn.flix.ui.favorites.FavoritesPagerFragment
import com.ardnn.flix.ui.movies.MoviesPagerFragment
import com.ardnn.flix.ui.tvshows.TvShowsPagerFragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set action bar
        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set bottom navigation
        binding.bnvHome.setOnItemSelectedListener(this)
        binding.bnvHome.itemIconTintList = null
        binding.bnvHome.selectedItemId = R.id.navigationMovies
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.navigationMovies -> {
                selectedFragment = MoviesPagerFragment()
                binding.toolbar.tvSection.text = getString(R.string.movies)
            }
            R.id.navigationTvShows -> {
                selectedFragment = TvShowsPagerFragment()
                binding.toolbar.tvSection.text = getString(R.string.tv_shows)
            }
            R.id.navigationFavorites -> {
                selectedFragment = FavoritesPagerFragment()
                binding.toolbar.tvSection.text = getString(R.string.favorites)
            }
        }

        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, selectedFragment)
                .commit()

            return true
        }

        return false
    }
}