package com.ardnn.flix.ui.home.film

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FilmEntity
import com.ardnn.flix.utils.FilmsData

class FilmViewModel(context: Context, section: Int) : ViewModel() {
    private val filmsData = FilmsData(context)

    private val _filmList = MutableLiveData<List<FilmEntity>>()
    val filmList: LiveData<List<FilmEntity>> = _filmList

    init {
        when (section) {
            0 -> { // movies
                _filmList.value = filmsData.movies
            }
            1 -> { // tv shows
                _filmList.value = filmsData.tvShows
            }
        }
    }
}