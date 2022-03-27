package com.ardnn.flix.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.flix.R
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.genre.model.Genre
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.databinding.FragmentMovieDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment(), View.OnClickListener, SingleClickListener<Genre> {

    private val viewModel: MovieDetailViewModel by viewModels()

    private var _binding: FragmentMovieDetailBinding? = null

    private val binding get() = _binding

    private lateinit var movie: MovieDetail

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_back -> {
                requireActivity().onBackPressed()
            }
            R.id.btn_favorite -> {
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
                            setMovieDetailToWidgets(it)
                            setPager(it)
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showAlert(true)

                        Timber.d(movieDetailResource.message.toString())
                        Toast.makeText(
                            requireActivity(),
                            "An error occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun setPager(movie: MovieDetail) {
        // set pager
        val pagerAdapter = MovieDetailPagerAdapter(requireActivity())
        pagerAdapter.setMovieDetail(movie)

        val viewPager = binding?.viewPager as ViewPager2
        viewPager.adapter = pagerAdapter

        // set tab layout
        val tabLayout = binding?.tabLayout as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = getString(TAB_TITLES[pos])
        }.attach()
        Helper.equalingEachTabWidth(tabLayout)
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

    private fun setMovieDetailToWidgets(movie: MovieDetail) {
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
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.about,
            R.string.casts
        )
    }
}