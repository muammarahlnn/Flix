package com.ardnn.flix.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.MyApplication
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.core.viewmodel.ViewModelFactory
import com.ardnn.flix.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoritesViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding

    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter
    private lateinit var favoriteTvShowsAdapter: FavoriteTvShowsAdapter

    private var section = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication)
            .appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.recyclerView)
        if (activity != null) {
            // get section and set it on view model
            section = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            viewModel.setSection(section)

            // set recyclerview
            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireActivity())

            // subscribe view model
            subscribe()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribe() {
        when (section) {
            0 -> { // movies
                viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favoriteMovies ->
                    setFavoriteMovies(favoriteMovies)
                })
            }
            1 -> { // tv shows
                viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { favoriteTvShows ->
                    setFavoriteTvShows(favoriteTvShows)
                })
            }
        }
    }

    private fun setFavoriteMovies(favoriteMovies: List<Movie>) {
        val pagedFavoriteMovies = PagedListDataSources.snapshot(favoriteMovies)
        favoriteMoviesAdapter = FavoriteMoviesAdapter()
        favoriteMoviesAdapter.submitList(pagedFavoriteMovies)
        binding?.recyclerView?.adapter = favoriteMoviesAdapter

        showAlert(favoriteMovies.isEmpty(), getString(R.string.movies))
    }

    private fun setFavoriteTvShows(favoriteTvShows: List<TvShow>) {
        val pagedFavoriteTvShows = PagedListDataSources.snapshot(favoriteTvShows)
        favoriteTvShowsAdapter = FavoriteTvShowsAdapter()
        favoriteTvShowsAdapter.submitList(pagedFavoriteTvShows)
        binding?.recyclerView?.adapter = favoriteTvShowsAdapter

        showAlert(favoriteTvShows.isEmpty(), getString(R.string.tv_shows))
    }

    private fun showAlert(flag: Boolean, type: String) {
        if (flag) {
            val alertText = getString(R.string.alert_favorite, type)
            binding?.tvAlert?.text = alertText

            binding?.tvAlert?.visibility = View.VISIBLE
            binding?.recyclerView?.visibility = View.GONE
        } else {
            binding?.tvAlert?.visibility = View.GONE
            binding?.recyclerView?.visibility = View.VISIBLE
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val snackBar = Snackbar.make(
                    view as View,
                    R.string.message_remove_favorite,
                    Snackbar.LENGTH_LONG)

                when (section) {
                    0 -> {
                        val movieDetail = favoriteMoviesAdapter.getSwipedData(swipedPosition)
                        movieDetail?.let {
                            viewModel.setIsFavoriteMovie(it)
                        }

                        snackBar.setAction(R.string.undo) {
                            movieDetail?.let {
                                viewModel.setIsFavoriteMovie(it)
                            }
                        }
                        snackBar.show()
                    }
                    1 -> {
                        val tvShowDetail = favoriteTvShowsAdapter.getSwipedData(swipedPosition)
                        tvShowDetail?.let {
                            viewModel.setIsFavoriteTvShow(it)
                        }

                        snackBar.setAction(R.string.undo) {
                            tvShowDetail?.let {
                                viewModel.setIsFavoriteTvShow(it)
                            }
                        }
                        snackBar.show()
                    }
                }
            }
        }

    })

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}