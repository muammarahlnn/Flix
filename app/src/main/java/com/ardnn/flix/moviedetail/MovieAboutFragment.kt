package com.ardnn.flix.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.FragmentFilmDetailAboutBinding

class MovieAboutFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentFilmDetailAboutBinding? = null

    private val binding get() = _binding

    private var isSynopsisExtended = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilmDetailAboutBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // get movie detail from args and set to widgets
            val movieDetail = arguments?.getParcelable<MovieDetail>(ARG_MOVIE_DETAIL) as MovieDetail
            setDataToWidgets(movieDetail)

            // click listener
            binding?.clWrapperSynopsis?.setOnClickListener(this)
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cl_wrapper_synopsis -> {
                isSynopsisExtended = !isSynopsisExtended
                if (isSynopsisExtended) {
                    binding?.tvSynopsis?.maxLines = Int.MAX_VALUE
                    binding?.tvMore?.text = getString(R.string.less)
                } else {
                    binding?.tvSynopsis?.maxLines = 2
                    binding?.tvMore?.text = getString(R.string.more)
                }
            }
        }
    }

    private fun setDataToWidgets(movieDetail: MovieDetail) {
        binding?.tvSynopsis?.text = Helper.setTextString(movieDetail.overview)
    }

    companion object {
        const val ARG_MOVIE_DETAIL = "movie_detail"
    }
}