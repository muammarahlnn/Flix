package com.ardnn.flix.tvshowdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.databinding.FragmentFilmDetailCastsBinding
import com.ardnn.flix.moviedetail.CastAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowCastsFragment : Fragment() {

    private val viewModel: TvShowDetailViewModel by viewModels()

    private var _binding: FragmentFilmDetailCastsBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailCastsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // get tv show id and set it into view model
            val tvShowId = arguments?.getInt(ARG_TV_SHOW_ID, 0) ?: 0
            viewModel.setTvShowId(tvShowId)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribe() {
        viewModel.tvShowCasts.observe(viewLifecycleOwner, { tvShowCastsResource ->
            if (tvShowCastsResource != null) {
                tvShowCastsResource.data?.let {  tvShowCasts ->
                    val pagedTvShowCasts = PagedListDataSources.snapshot(tvShowCasts)
                    val adapter = CastAdapter()
                    adapter.submitList(pagedTvShowCasts)
                    binding?.rvCast?.adapter = adapter
                }
            }
        })
    }

    companion object {
        const val ARG_TV_SHOW_ID = "tv_show_id"
    }
}