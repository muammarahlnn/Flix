package com.ardnn.flix.core.domain.moviedetail.model

data class Cast(
    val id: Int,
    val filmId: Int,
    var name: String = "",
    var character: String = "",
    var profileUrl: String = "",
)