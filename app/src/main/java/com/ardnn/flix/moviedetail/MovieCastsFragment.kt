package com.ardnn.flix.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.databinding.FragmentFilmDetailCastsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieCastsFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private var _binding: FragmentFilmDetailCastsBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilmDetailCastsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // get movie id and set it into view model
            val movieId = arguments?.getInt(ARG_MOVIE_ID, 0) ?: 0
            viewModel.setMovieId(movieId)

            // set recyclerview cast
            binding?.rvCast?.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )

            // subscribe view model
            subscribe()
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.root?.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribe() {
        viewModel.movieCasts.observe(viewLifecycleOwner, { movieCastsResource ->
            if (movieCastsResource != null) {
                when (movieCastsResource) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)

                        movieCastsResource.data?.let { movieCasts ->
                            val pagedMovieCasts = PagedListDataSources.snapshot(movieCasts)
                            val adapter = CastAdapter()
                            adapter.submitList(pagedMovieCasts)
                            binding?.rvCast?.adapter = adapter
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.rvCast?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.rvCast?.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movie_id"
    }
}