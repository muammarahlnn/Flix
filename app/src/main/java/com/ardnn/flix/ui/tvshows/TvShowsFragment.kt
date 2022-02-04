package com.ardnn.flix.ui.tvshows

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.databinding.FragmentTvShowsBinding
import com.ardnn.flix.ui.tvshow_detail.TvShowDetailActivity
import com.ardnn.flix.utils.SingleClickListener
import com.ardnn.flix.viewmodel.ViewModelFactory

class TvShowsFragment : Fragment(), SingleClickListener<TvShowEntity> {

    private lateinit var viewModel: TvShowsViewModel
    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding

    private val page = 1 // default page to fetch movies
    private var section = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // initialize view model
            val factory = ViewModelFactory.getInstance()
            viewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]

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
        setTvShows()

        viewModel.getIsLoading().observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })
    }

    private fun setTvShows() {
        when (section) {
            0 -> { // on the air
                viewModel.getTvShows(page).observe(viewLifecycleOwner, { tvShowList ->
                    val adapter = TvShowsAdapter(tvShowList, this)
                    binding?.recyclerView?.adapter = adapter
                })
            }
            1 -> { // top rated
                viewModel.getTvShows(page).observe(viewLifecycleOwner, { tvShowList ->
                    val adapter = TvShowsAdapter(tvShowList, this)
                    binding?.recyclerView?.adapter = adapter
                })
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClicked(item: TvShowEntity) {
        val toTvShowDetail = Intent(requireActivity(), TvShowDetailActivity::class.java)
        toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, item.id)
        startActivity(toTvShowDetail)
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}