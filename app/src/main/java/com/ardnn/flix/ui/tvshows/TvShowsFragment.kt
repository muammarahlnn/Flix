package com.ardnn.flix.ui.tvshows

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.databinding.FragmentFilmsBinding
import com.ardnn.flix.ui.tvshowdetail.TvShowDetailActivity
import com.ardnn.flix.util.PagedListDataSources
import com.ardnn.flix.util.SingleClickListener
import com.ardnn.flix.util.SortUtils
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Resource
import com.ardnn.flix.vo.Status

class TvShowsFragment : Fragment(), SingleClickListener<TvShowEntity> {

    private lateinit var viewModel: TvShowsViewModel
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
            // initialize view model
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvShowsViewModel::class.java]

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
        viewModel.getSectionWithTvShows(page, sort).observe(viewLifecycleOwner, { sectionWithTvShowsResource ->
            if (sectionWithTvShowsResource != null) {
                setTvShows(sectionWithTvShowsResource)
            }
        })
        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        viewModel.getSectionWithTvShows(page, SortUtils.DEFAULT).observe(viewLifecycleOwner, { sectionWithTvShowsResource ->
            if (sectionWithTvShowsResource != null) {
                setTvShows(sectionWithTvShowsResource)
            }
        })
    }

    private fun setTvShows(tvShowsResource: Resource<List<TvShowEntity>>) {
        when (tvShowsResource.status) {
            Status.LOADING -> {
                showLoading(true)
                showAlert(false)
            }
            Status.SUCCESS -> {
                if (tvShowsResource.data != null) {
                    showLoading(false)
                    showAlert(false)

                    val tvShows = tvShowsResource.data
                    val pagedTvShows = PagedListDataSources.snapshot(tvShows)
                    val adapter = TvShowsAdapter(this)
                    adapter.submitList(pagedTvShows)
                    binding?.recyclerView?.adapter = adapter
                }
            }
            Status.ERROR -> {
                showLoading(false)
                showAlert(true)

                Log.d(TAG, tvShowsResource.message.toString())
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

    override fun onItemClicked(item: TvShowEntity) {
        val toTvShowDetail = Intent(requireActivity(), TvShowDetailActivity::class.java)
        toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, item.id)
        startActivity(toTvShowDetail)
    }

    companion object {
        private const val TAG = "TvShowsFragment"
        const val ARG_SECTION_NUMBER = "section_number"
    }

}