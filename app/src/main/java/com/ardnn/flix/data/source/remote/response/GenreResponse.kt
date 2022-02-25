package com.ardnn.flix.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,
)