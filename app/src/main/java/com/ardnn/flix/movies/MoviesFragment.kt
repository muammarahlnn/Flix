package com.ardnn.flix.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.core.util.SortUtils
import com.ardnn.flix.core.viewmodel.ViewModelFactory
import com.ardnn.flix.core.vo.Resource
import com.ardnn.flix.databinding.FragmentFilmsBinding
import com.ardnn.flix.moviedetail.MovieDetailActivity

class MoviesFragment : Fragment(), SingleClickListener<Movie> {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var movieAdapter: TempMoviesAdapter
    private var _binding: FragmentFilmsBinding? = null
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
        _binding = FragmentFilmsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            movieAdapter = TempMoviesAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, selectedData.id)
                startActivity(intent)
            }

            // initialize view model
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

            // get section and set it on view model
            section = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            viewModel.setSection(section)

            // subscribe view model
            subscribe()

            with(binding?.recyclerView) {
                this?.setHasFixedSize(true)
                this?.adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.menu_films, menu)

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
        viewModel.getSectionWithMovies(page, sort).observe(viewLifecycleOwner, { moviesResource ->
            if (moviesResource != null) {
                setMovies(moviesResource)
            }
        })
        item.isChecked = true

        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        viewModel.getSectionWithMovies(page, SortUtils.DEFAULT).observe(viewLifecycleOwner, { sectionWithMoviesResource ->
            if (sectionWithMoviesResource != null) {
                setMovies(sectionWithMoviesResource)
            }
        })
    }

    private fun setMovies(moviesResource: Resource<List<Movie>>) {
        when (moviesResource) {
            is Resource.Loading -> {
                showLoading(true)
                showAlert(false)
            }
            is Resource.Success -> {
                Log.d(TAG, "Success")
                Log.d(TAG, "Empty == ${moviesResource.data?.isEmpty()}")
                Log.d(TAG, "Success -> IN")
                showLoading(false)
                showAlert(false)

                movieAdapter.setData(moviesResource.data)

//                val movies = moviesResource.data as List<Movie>
//                val pagedMovies = PagedListDataSources.snapshot(movies)
//                val adapter = MoviesAdapter(this)
//                adapter.submitList(pagedMovies)
//                binding?.recyclerView?.adapter = adapter
            }
            is Resource.Error -> {
                showLoading(false)
                showAlert(true)

                Log.d(TAG, moviesResource.message.toString())
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

    private fun showAlert(isFailure: Boolean) {
        if (isFailure) {
            binding?.tvAlert?.visibility = View.VISIBLE
            binding?.recyclerView?.visibility = View.GONE
        } else {
            binding?.tvAlert?.visibility = View.GONE
            binding?.recyclerView?.visibility = View.VISIBLE
        }
    }

    override fun onItemClicked(item: Movie) {
        val toMovieDetail = Intent(requireActivity(), MovieDetailActivity::class.java)
        toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, item.id)
        startActivity(toMovieDetail)
    }

    companion object {
        private const val TAG = "MoviesFragment"
        const val ARG_SECTION_NUMBER = "section_number"
    }

}