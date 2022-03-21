package com.ardnn.flix.core.domain.moviedetail.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val id: Int,
    val filmId: Int,
    var name: String = "",
    var character: String = "",
    var profileUrl: String = "",
) : Parcelable