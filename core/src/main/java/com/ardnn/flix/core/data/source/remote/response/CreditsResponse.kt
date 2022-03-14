package com.ardnn.flix.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @field:SerializedName("cast")
    val cast: List<CastResponse>
)

data class CastResponse(
    @field:SerializedName("id")
    val id: Int = -1,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("character")
    val character: String? = null,

    @field:SerializedName("profile_path")
    val profileUrl: String? = null
)

