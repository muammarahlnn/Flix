package com.ardnn.flix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ardnn.flix.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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