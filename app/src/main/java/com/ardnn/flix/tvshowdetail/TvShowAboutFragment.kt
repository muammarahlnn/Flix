package com.ardnn.flix.tvshowdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.FragmentFilmDetailAboutBinding

class TvShowAboutFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentFilmDetailAboutBinding? = null

    private val binding get() = _binding

    private var isSynopsisExtended = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailAboutBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            // get tv show detail from args and set to widgets
            val tvShowDetail = arguments?.getParcelable<TvShowDetail>(ARG_TV_SHOW_DETAIL) as TvShowDetail
            setDataToWidgets(tvShowDetail)

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
            R.id.clWrapperSynopsis -> {
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

    private fun setDataToWidgets(tvShowDetail: TvShowDetail) {
        binding?.tvSynopsis?.text = Helper.setTextString(tvShowDetail.overview)
    }

    companion object {
        const val ARG_TV_SHOW_DETAIL = "tv_show_detail"
    }

}