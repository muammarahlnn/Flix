package com.ardnn.flix.ui.movies

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.flix.R
import com.ardnn.flix.databinding.FragmentMoviesPagerBinding
import com.ardnn.flix.utils.Helper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MoviesPagerFragment : Fragment() {

    private var _binding: FragmentMoviesPagerBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesPagerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // set pager
            val viewPager = binding?.viewPager as ViewPager2
            val pagerAdapter = MoviesPagerAdapter(requireActivity())
            viewPager.adapter = pagerAdapter

            // set tab layout
            val tabLayout = binding?.tabLayout as TabLayout
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = getString(TAB_TITLES[pos])
            }.attach()
            Helper.equalingEachTabWidth(tabLayout)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.now_playing,
            R.string.upcoming,
            R.string.popular,
            R.string.top_rated
        )
    }
}