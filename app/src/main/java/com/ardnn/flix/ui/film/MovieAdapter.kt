package com.ardnn.flix.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.api.ImageSize
import com.ardnn.flix.api.response.Movie
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.utils.FilmClickListener
import com.ardnn.flix.utils.Helper

class MovieAdapter(
    private val movieList: List<Movie>,
    private val filmClickListener: FilmClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: Movie) {
            with (binding) {
                if (movie.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        movie.getPosterUrl(ImageSize.W500),
                        ivPoster)
                }

                tvTitle.text = movie.title ?: "-"
                tvYear.text =
                    if (movie.posterUrl.isNullOrEmpty()) "-"
                    else movie.releaseDate.toString().substring(0, 4)
                tvRating.text = (movie.rating ?: "-").toString()
            }

            itemView.setOnClickListener {
                filmClickListener.onMovieClicked(movie)
            }
        }
    }

}
