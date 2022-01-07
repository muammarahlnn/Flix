package com.ardnn.flix.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.data.FilmEntity
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.utils.ClickListener
import com.ardnn.flix.utils.Helper

class FilmAdapter(
    private val filmList: List<FilmEntity>,
    private val clickListener: ClickListener<FilmEntity>
) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(filmList[position])
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    inner class ViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(film: FilmEntity) {
            with (binding) {
                Helper.setImageGlide(itemView.context, film.poster, ivPoster)
                tvTitle.text = film.title
                tvYear.text = film.releaseDate.substring(film.releaseDate.length - 4)
                tvRating.text = film.rating.toString()
            }

            itemView.setOnClickListener {
                clickListener.onClicked(film)
            }
        }
    }
}