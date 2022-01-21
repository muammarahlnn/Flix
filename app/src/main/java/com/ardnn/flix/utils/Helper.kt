package com.ardnn.flix.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ardnn.flix.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout

object Helper {
    fun equalingEachTabWidth(tabLayout: TabLayout) {
        val slidingTab: ViewGroup = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabLayout.tabCount) {
            val tab: View = slidingTab.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1F
            tab.layoutParams = layoutParams
        }
    }

    fun setImageGlide(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_error))
            .into(imageView)
    }
}