package com.ardnn.flix.ui.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.databinding.FragmentMoviesBinding
import com.ardnn.flix.ui.movie_detail.MovieDetailActivity
import com.ardnn.flix.utils.SingleClickListener
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Resource
import com.ardnn.flix.vo.Status

class MoviesFragment : Fragment(), SingleClickListener<MovieEntity> {

    private lateinit var viewModel: MoviesViewModel
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding

    private val page = 1 // default page to fetch movies
    private var section = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // initialize view model
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

            // get section and set it on view model
            section = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            viewModel.setSection(section)

            // set recyclerview
            binding?.recyclerView?.layoutManager = GridLayoutManager(requireActivity(), 2)

            // subscribe view model
            subscribe()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribe() {
        // set movies depends on section
        when (section) {
            0 -> { // now playing
                viewModel.getNowPlayingMovies(page).observe(viewLifecycleOwner, { moviesResource ->
                    if (moviesResource != null) {
                        setMovies(moviesResource)
                    }
                })
            }
            1 -> { // top rated
                viewModel.getTopRatedMovies(page).observe(viewLifecycleOwner, { moviesResource ->
                    if (moviesResource != null) {
                        setMovies(moviesResource)
                    }
                })
            }
        }
    }

    private fun setMovies(moviesResource: Resource<List<MovieEntity>>) {
        when (moviesResource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.SUCCESS -> {
                if (moviesResource.data != null) {
                    showLoading(false)

                    val adapter = MoviesAdapter(moviesResource.data, this)
                    binding?.recyclerView?.adapter = adapter
                }
            }
            Status.ERROR -> {
                showLoading(false)
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClicked(item: MovieEntity) {
        val toMovieDetail = Intent(requireActivity(), MovieDetailActivity::class.java)
        toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, item.id)
        startActivity(toMovieDetail)
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}