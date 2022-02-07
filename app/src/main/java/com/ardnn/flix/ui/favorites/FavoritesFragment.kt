package com.ardnn.flix.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.databinding.FragmentFavoritesBinding
import com.ardnn.flix.viewmodel.ViewModelFactory

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding

    private var section = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // initialize view model
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]

            // get section and set it on view model
            section = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            viewModel.setSection(section)

            // set recyclerview
            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())

            // subscribe view model
            subscribe()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribe() {
        when (section) {
            0 -> { // movies
                viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favoriteMovies ->
                    val adapter = FavoriteMoviesAdapter()
                    adapter.submitList(favoriteMovies)
                    binding?.recyclerView?.adapter = adapter
                })
            }
            1 -> { // tv shows
                viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { favoriteTvShows ->
                    val adapter = FavoriteTvShowsAdapter()
                    adapter.submitList(favoriteTvShows)
                    binding?.recyclerView?.adapter = adapter
                })
            }
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}