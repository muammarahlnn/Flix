package com.ardnn.flix.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.utils.Helper
import com.ardnn.flix.utils.SingleClickListener

class MoviesAdapter(
    private val movieList: List<MovieEntity>,
    private val clickListener: SingleClickListener<MovieEntity>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: MovieEntity) {
            with (binding) {
                if (movie.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        movie.getPosterUrl(ImageSize.W342),
                        ivPoster)
                }

                tvTitle.text = movie.title ?: "-"
                tvYear.text =
                    if (movie.posterUrl.isNullOrEmpty()) "-"
                    else movie.releaseDate.toString().substring(0, 4)
                tvRating.text = (movie.rating ?: "-").toString()
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(movie)
            }
        }
    }

}