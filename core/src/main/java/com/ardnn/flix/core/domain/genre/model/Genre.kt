package com.ardnn.flix.core.domain.genre.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    var name: String = ""
) : Parcelable