package com.ardnn.flix.ui.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.ardnn.flix.R
import com.ardnn.flix.databinding.FragmentFavoritesPagerBinding
import com.ardnn.flix.util.Helper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesPagerFragment : Fragment() {

    private var _binding: FragmentFavoritesPagerBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesPagerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // set pager
            val viewPager = binding?.viewPager as ViewPager2
            val pagerAdapter = FavoritesPagerAdapter(requireActivity())
            viewPager.adapter = pagerAdapter

            // set tab layout
            val tabLayout = binding?.tabLayout as TabLayout
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = getString(TAB_TITLES[pos])
            }.attach()
            Helper.equalingEachTabWidth(tabLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movies,
            R.string.tv_shows
        )
    }
}