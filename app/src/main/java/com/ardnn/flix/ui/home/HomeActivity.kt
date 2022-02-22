package com.ardnn.flix.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ardnn.flix.R
import com.ardnn.flix.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set action bar
        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set bottom navigation
        val navView: BottomNavigationView = binding.navHome
        val navController = findNavController(R.id.navHostFragmentHome)

        // set toolbar title depends on selected fragment
        navController.addOnDestinationChangedListener { controller, destination, args ->
            binding.toolbar.tvSection.text = navController.currentDestination?.label
        }

        navView.setupWithNavController(navController)
        navView.itemIconTintList = null // to make item icon do not use app primary color

    }
}