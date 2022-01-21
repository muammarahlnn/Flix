package com.ardnn.flix.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FilmEntity

class DetailViewModel(film: FilmEntity) : ViewModel() {

    private val _film = MutableLiveData<FilmEntity>()
    val film : LiveData<FilmEntity> = _film

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    init {
        _film.value = film
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }

}