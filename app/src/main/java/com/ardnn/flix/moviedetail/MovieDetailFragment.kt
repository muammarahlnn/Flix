package com.ardnn.flix.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(), View.OnClickListener, SingleClickListener<Genre> {

    private val viewModel: MovieDetailViewModel by viewModels()

    private var _binding: FragmentMovieDetailBinding? = null

    private val binding get() = _binding

    private lateinit var movie: Movie

    private var isSynopsisExtended = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // get movie id and set it into view model
            val movieId = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).id
            viewModel.setMovieId(movieId)

            // set recyclerview genre
            binding?.rvGenre?.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            // subscribe view model
            subscribe()

            // click listener
            binding?.btnBack?.setOnClickListener(this)
            binding?.btnFavorite?.setOnClickListener(this)
            binding?.clWrapperSynopsis?.setOnClickListener(this)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                requireActivity().onBackPressed()
            }
            R.id.btnFavorite -> {
                viewModel.setIsFavorite()
                if (!movie.isFavorite) {
                    Toast.makeText(
                        requireActivity(),
                        "${movie.title} has added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "${movie.title} has removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.clWrapperSynopsis -> {
                isSynopsisExtended = !isSynopsisExtended
                viewModel.setIsSynopsisExtended(isSynopsisExtended)
            }
        }
    }

    private fun subscribe() {
        viewModel.movie.observe(viewLifecycleOwner, { movieDetailResource ->
            if (movieDetailResource != null) {
                when (movieDetailResource) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showAlert(false)

                        movieDetailResource.data?.let {
                            movie = it
                            setMovieDetailToWidgets()
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showAlert(true)

                        Log.d(TAG, movieDetailResource.message.toString())
                        Toast.makeText(
                            requireActivity(),
                            "An error occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        viewModel.isSynopsisExtended.observe(viewLifecycleOwner, { isExtended ->
            if (isExtended) {
                binding?.tvSynopsis?.maxLines = Int.MAX_VALUE
                binding?.tvMore?.text = getString(R.string.less)
            } else {
                binding?.tvSynopsis?.maxLines = 2
                binding?.tvMore?.text = getString(R.string.more)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.clAlert?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.tvAlert?.visibility = View.GONE
        } else {
            binding?.clAlert?.visibility = View.GONE
        }
    }

    private fun showAlert(isFailure: Boolean) {
        if (isFailure) {
            binding?.clAlert?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.GONE
            binding?.tvAlert?.visibility = View.VISIBLE
        } else {
            binding?.clAlert?.visibility = View.GONE
        }
    }

    private fun setMovieDetailToWidgets() {
        binding?.run {
            // set images
            Helper.setImageGlide(
                requireActivity(),
                Helper.getPosterTMDB(movie.posterUrl),
                ivPoster
            )
            Helper.setImageGlide(
                requireActivity(),
                Helper.getWallpaperTMDB(movie.wallpaperUrl),
                ivWallpaper
            )

            // set btn favorite
            val isFavorite = movie.isFavorite
            btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.setTextString(movie.title)
            tvReleaseDate.text = Helper.setTextDate(movie.releaseDate)
            tvRuntime.text = Helper.setTextRuntime(requireActivity(), movie.runtime)
            tvRating.text = Helper.setTextFloat(movie.rating)
            tvSynopsis.text = Helper.setTextString(movie.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(movie.genres, this@MovieDetailFragment)
            rvGenre.adapter = genreAdapter
        }
    }

    override fun onItemClicked(item: Genre) {
        val toGenre = MovieDetailFragmentDirections
            .actionMovieDetailFragmentToGenreFragment().apply {
                id = item.id
                name = item.name
                type = getString(R.string.movies)
            }
        findNavController().navigate(toGenre)
    }

    companion object {
        private const val TAG = "MovieDetailFragment"
    }
}