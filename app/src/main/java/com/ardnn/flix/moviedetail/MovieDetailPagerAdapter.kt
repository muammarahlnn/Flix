package com.ardnn.flix.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail

class MovieDetailPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private lateinit var movieDetail: MovieDetail

    fun setMovieDetail(movieDetail: MovieDetail) {
        this.movieDetail = movieDetail
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> { // about
                fragment = MovieAboutFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable(MovieAboutFragment.ARG_MOVIE_DETAIL, movieDetail)
                }
            }
            1 -> { // casts
                fragment = MovieCastsFragment()
                fragment.arguments = Bundle().apply {
                    putInt(MovieCastsFragment.ARG_MOVIE_ID, movieDetail.id)
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