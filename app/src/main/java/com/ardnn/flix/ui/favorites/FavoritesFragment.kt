package com.ardnn.flix.ui.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.databinding.FragmentFavoritesBinding
import com.ardnn.flix.utils.SortUtils
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter
    private lateinit var favoriteTvShowsAdapter: FavoriteTvShowsAdapter

    private var section = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        itemTouchHelper.attachToRecyclerView(binding?.recyclerView)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.menu_favorites, menu)

        when (section) {
            0 -> { // movies
                viewModel.moviesSort.observe(viewLifecycleOwner, { filter ->
                    setCheckedSort(menu, filter)
                })
            }
            1 -> { // tv shows
                viewModel.tvShowsSort.observe(viewLifecycleOwner, { filter ->
                    setCheckedSort(menu, filter)
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_default -> sort = SortUtils.DEFAULT
            R.id.action_ascending -> sort = SortUtils.ASCENDING
            R.id.action_descending -> sort = SortUtils.DESCENDING
            R.id.action_random -> sort = SortUtils.RANDOM
        }

        when (section) {
            0 -> { // movies
                viewModel.setMoviesSort(sort)
                viewModel.getFavoriteMovies(sort).observe(viewLifecycleOwner, { favoriteMovies ->
                    setFavoriteMovies(favoriteMovies)
                })
            }
            1 -> { // tv shows
                viewModel.setTvShowsSort(sort)
                viewModel.getFavoriteTvShows(sort).observe(viewLifecycleOwner, { favoriteTvShows ->
                    setFavoriteTvShows(favoriteTvShows)
                })
            }
        }
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        when (section) {
            0 -> { // movies
                viewModel.getFavoriteMovies(SortUtils.DEFAULT).observe(viewLifecycleOwner, { favoriteMovies ->
                    setFavoriteMovies(favoriteMovies)
                })
            }
            1 -> { // tv shows
                viewModel.getFavoriteTvShows(SortUtils.DEFAULT).observe(viewLifecycleOwner, { favoriteTvShows ->
                    setFavoriteTvShows(favoriteTvShows)
                })
            }
        }
    }

    private fun setFavoriteMovies(favoriteMovies: PagedList<MovieDetailEntity>) {
        favoriteMoviesAdapter = FavoriteMoviesAdapter()
        favoriteMoviesAdapter.submitList(favoriteMovies)
        binding?.recyclerView?.adapter = favoriteMoviesAdapter

        showAlert(favoriteMovies.isEmpty(), getString(R.string.movies))
    }

    private fun setFavoriteTvShows(favoriteTvShows: PagedList<TvShowDetailEntity>) {
        favoriteTvShowsAdapter = FavoriteTvShowsAdapter()
        favoriteTvShowsAdapter.submitList(favoriteTvShows)
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

    private fun setCheckedSort(menu: Menu, filter: String) {
        when (filter) {
            SortUtils.DEFAULT -> {
                menu.findItem(R.id.action_default).isChecked = true
            }
            SortUtils.ASCENDING -> {
                menu.findItem(R.id.action_ascending).isChecked = true
            }
            SortUtils.DESCENDING -> {
                menu.findItem(R.id.action_descending).isChecked = true
            }
            SortUtils.RANDOM -> {
                menu.findItem(R.id.action_random).isChecked = true
            }
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