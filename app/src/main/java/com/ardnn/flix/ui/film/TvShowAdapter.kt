package com.ardnn.flix.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.utils.FilmClickListener
import com.ardnn.flix.utils.Helper

class TvShowAdapter(
    private val tvShowList: List<TvShowEntity>,
    private val filmClickListener: FilmClickListener
) : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(tvShowList[position])
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    inner class ViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShow: TvShowEntity) {
            with (binding) {
                if (tvShow.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        tvShow.getPosterUrl(ImageSize.W342),
                        ivPoster)
                }

                tvTitle.text = tvShow.title ?: "-"
                tvYear.text =
                    if (tvShow.posterUrl.isNullOrEmpty()) "-"
                    else tvShow.firstAirDate.toString().substring(0, 4)
                tvRating.text = (tvShow.rating ?: "-").toString()
            }

            itemView.setOnClickListener {
                filmClickListener.onTvShowClicked(tvShow)
            }
        }

    }

}