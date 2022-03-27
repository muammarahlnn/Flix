package com.ardnn.flix.tvshows

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ardnn.flix.MainFragmentDirections
import com.ardnn.flix.R
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.tvshows.model.TvShow
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.core.util.SortUtils
import com.ardnn.flix.databinding.FragmentFilmsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TvShowsFragment : Fragment(), SingleClickListener<TvShow> {

    private val viewModel: TvShowsViewModel by viewModels()

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

            // get section and set it on view model
            section = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
            viewModel.setSection(section)

            // subscribe view model
            subscribe()
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

        viewModel.tvShowsSort.observe(viewLifecycleOwner, { filters ->
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

        viewModel.setTvShowsSort(sort)
        viewModel.getTvShows(page, sort).observe(viewLifecycleOwner, { tvShowsResource ->
            if (tvShowsResource != null) {
                setTvShows(tvShowsResource)
            }
        })
        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        viewModel.getTvShows(page, SortUtils.DEFAULT).observe(viewLifecycleOwner, { tvShowsResource ->
            if (tvShowsResource != null) {
                setTvShows(tvShowsResource)
            }
        })
    }

    private fun setTvShows(tvShowsResource: Resource<List<TvShow>>) {
        when (tvShowsResource) {
            is Resource.Loading -> {
                showLoading(true)
                showAlert(false)
            }
            is Resource.Success -> {
                showLoading(false)
                showAlert(false)

                tvShowsResource.data?.let {
                    val pagedTvShows = PagedListDataSources.snapshot(it)
                    val adapter = TvShowsAdapter(this)
                    adapter.submitList(pagedTvShows)
                    binding?.recyclerView?.adapter = adapter
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showAlert(true)

                Timber.d(tvShowsResource.message.toString())
                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
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

    override fun onItemClicked(item: TvShow) {
        val toTvShowDetail = MainFragmentDirections
            .actionMainFragmentToTvShowDetailFragment().apply {
                id = item.id
            }
        findNavController().navigate(toTvShowDetail)
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}