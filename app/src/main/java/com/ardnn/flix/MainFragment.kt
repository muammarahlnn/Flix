package com.ardnn.flix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ardnn.flix.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // set action bar
            (activity as AppCompatActivity).apply {
                setSupportActionBar(binding?.toolbar?.root)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }

            // get nav host and nav controller
            val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_home) as? NavHostFragment
            val navController = nestedNavHostFragment?.navController

            // set toolbar title depends on selected fragment
            navController?.addOnDestinationChangedListener { _, _, _ ->
                binding?.toolbar?.tvSection?.text = navController.currentDestination?.label
            }

            // setup bottom nav view
            val navView = binding?.navHome
            navView?.setupWithNavController(navController as NavController)
            navView?.itemIconTintList = null // to make item icon do not use app primary color
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}