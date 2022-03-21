package com.ardnn.flix.tvshowdetail

import android.os.Bundle
import android.util.Log
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
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.databinding.FragmentTvShowDetailBinding
import com.ardnn.flix.moviedetail.GenreAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailFragment : Fragment(), View.OnClickListener, SingleClickListener<Genre> {

    private val viewModel: TvShowDetailViewModel by viewModels()

    private var _binding: FragmentTvShowDetailBinding? = null

    private val binding get() = _binding

    private lateinit var tvShow: TvShowDetail

    private var isSynopsisExtended = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // get tv show id and set it into view model
            val tvShowId = TvShowDetailFragmentArgs.fromBundle(arguments as Bundle).id
            viewModel.setTvShowId(tvShowId)

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
        when(v.id) {
            R.id.btn_back -> {
                requireActivity().onBackPressed()
            }
            R.id.btn_favorite -> {
                viewModel.setIsFavorite()
                if (!tvShow.isFavorite) {
                    Toast.makeText(
                        requireActivity(),
                        "${tvShow.title} has added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "${tvShow.title} has removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun subscribe() {
        viewModel.tvShow.observe(this, { tvShowResource ->
            if (tvShowResource != null) {
                when (tvShowResource) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showAlert(false)

                        tvShowResource.data?.let {
                            tvShow = it
                            setTvShowDetailToWidgets(it)
                            setPager(it)
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showAlert(true)

                        Log.d(TAG, tvShowResource.message.toString())
                        Toast.makeText(requireActivity(), "An error occurred", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun setPager(tvShow: TvShowDetail) {
        // set pager
        val pagerAdapter = TvShowDetailPagerAdapter(requireActivity())
        pagerAdapter.setTvShowDetail(tvShow)

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

    private fun setTvShowDetailToWidgets(tvShow: TvShowDetail) {
        binding?.run {
            // set images
            Helper.setImageGlide(
                requireActivity(),
                Helper.getPosterTMDB(tvShow.posterUrl),
                ivPoster
            )
            Helper.setImageGlide(
                requireActivity(),
                Helper.getWallpaperTMDB(tvShow.wallpaperUrl),
                ivWallpaper
            )

            // set btn favorite
            val isFavorite = tvShow.isFavorite
            btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.setTextString(tvShow.title)
            tvEpisodes.text = Helper.setTextNum(tvShow.numberOfEpisodes)
            tvSeasons.text = Helper.setTextNum(tvShow.numberOfSeasons)
            tvRuntime.text = Helper.setTextRuntime(requireActivity(), tvShow.runtime)
            tvRating.text = Helper.setTextFloat(tvShow.rating)
            tvFirstAiring.text = Helper.setTextDate(tvShow.firstAirDate)
            tvLastAiring.text = Helper.setTextDate(tvShow.lastAirDate)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(tvShow.genres, this@TvShowDetailFragment)
            rvGenre.adapter = genreAdapter
        }
    }

    override fun onItemClicked(item: Genre) {
        val toGenre = TvShowDetailFragmentDirections
            .actionTvShowDetailFragmentToGenreFragment().apply {
                id = item.id
                name = item.name
                type = getString(R.string.tv_shows)
            }
        findNavController().navigate(toGenre)
    }

    companion object {
        private const val TAG = "TvShowDetailFragment"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.about,
            R.string.casts
        )
    }

}