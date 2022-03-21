package com.ardnn.flix.tvshowdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail

class TvShowDetailPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity){

    private lateinit var tvShowDetail: TvShowDetail

    fun setTvShowDetail(tvShowDetail: TvShowDetail) {
        this.tvShowDetail = tvShowDetail
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> { // about
                fragment = TvShowAboutFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable(TvShowAboutFragment.ARG_TV_SHOW_DETAIL, tvShowDetail)
                }
            }
            1 -> { // casts
                fragment = TvShowCastsFragment()
                fragment.arguments = Bundle().apply {
                    putInt(TvShowCastsFragment.ARG_TV_SHOW_ID, tvShowDetail.id)
                }
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        // [about, casts]
        return 2
    }

}