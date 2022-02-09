package com.ardnn.flix.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.databinding.FragmentMoviesBinding
import com.ardnn.flix.ui.movie_detail.MovieDetailActivity
import com.ardnn.flix.utils.SingleClickListener
import com.ardnn.flix.utils.SortUtils
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Resource
import com.ardnn.flix.vo.Status

class MoviesFragment : Fragment(), SingleClickListener<MovieEntity> {

    private lateinit var viewModel: MoviesViewModel
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding

    private val page = 1 // default page to fetch movies
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.menu_favorites, menu)

        viewModel.moviesSort.observe(viewLifecycleOwner, { filters ->
            when (filters[section]) {
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
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_default -> sort = SortUtils.DEFAULT
            R.id.action_ascending -> sort = SortUtils.ASCENDING
            R.id.action_descending -> sort = SortUtils.DESCENDING
            R.id.action_random -> sort = SortUtils.RANDOM
        }

        viewModel.setMoviesSort(sort)
        viewModel.getMovies(page, sort).observe(viewLifecycleOwner, { moviesResource ->
            if (moviesResource != null) {
                setMovies(moviesResource)
            }
        })
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        viewModel.getMovies(page, SortUtils.DEFAULT).observe(viewLifecycleOwner, { moviesResource ->
            if (moviesResource != null) {
                setMovies(moviesResource)
            }
        })
    }

    private fun setMovies(moviesResource: Resource<PagedList<MovieEntity>>) {
        when (moviesResource.status) {
            Status.LOADING -> {
                showLoading(true)
            }
            Status.SUCCESS -> {
                if (moviesResource.data != null) {
                    showLoading(false)

                    val adapter = MoviesAdapter(this)
                    adapter.submitList(moviesResource.data)
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
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.recyclerView?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.recyclerView?.visibility = View.VISIBLE
        }
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