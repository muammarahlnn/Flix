package com.ardnn.flix.genre

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ardnn.flix.R
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.databinding.FragmentGenreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : Fragment() {

    private val viewModel: GenreViewModel by viewModels()

    private var _binding: FragmentGenreBinding? = null

    private val binding get() = _binding

    private var genreType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGenreBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // setup action bar
            (activity as AppCompatActivity).apply {
                setSupportActionBar(binding?.toolbar?.root)
                supportActionBar?.setDisplayShowTitleEnabled(false)
                supportActionBar?.setHomeButtonEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            binding?.toolbar?.ivIcon?.visibility = View.GONE

            // set action bar title
            val genreName = GenreFragmentArgs.fromBundle(arguments as Bundle).name
            genreType = GenreFragmentArgs.fromBundle(arguments as Bundle).type
            binding?.toolbar?.tvSection?.text = getString(
                R.string.genre_toolbar_title, genreName, genreType)

            // get id and set it on view model
            val genreId = GenreFragmentArgs.fromBundle(arguments as Bundle).id
            viewModel.setGenreId(genreId)

            // subscribe view model
            subscribe()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        // hide group sort menu in action bar
        for (item in menu.iterator()) {
            item.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // if btn back in action bar clicked
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        when (genreType) {
            getString(R.string.movies) -> {
                viewModel.getGenreWithMovies().observe(this, { genre ->
                    val movies = genre.movies
                    val pagedMovies = PagedListDataSources.snapshot(movies)
                    val adapter = GenreMoviesAdapter()
                    adapter.submitList(pagedMovies)
                    binding?.recyclerView?.adapter = adapter
                })
            }
            getString(R.string.tv_shows) -> {
                viewModel.getGenreWithTvShows().observe(this, { genre ->
                    val tvShows = genre.tvShows
                    val pagedTvShows = PagedListDataSources.snapshot(tvShows)
                    val adapter = GenreTvShowsAdapter()
                    adapter.submitList(pagedTvShows)
                    binding?.recyclerView?.adapter = adapter
                })
            }
        }
    }

}