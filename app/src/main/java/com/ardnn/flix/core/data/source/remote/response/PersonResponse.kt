package com.ardnn.flix.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @field:SerializedName("id")
    val id: Int = -1,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("gender")
    val gender: Int? = null,

    @field:SerializedName("birthday")
    val birthday: String? = null,

    @field:SerializedName("place_of_birth")
    val birthPlace: String? = null,

    @field:SerializedName("profile_path")
    val profileUrl: String? = null,

    @field:SerializedName("known_for_department")
    val department: String? = null,

    @field:SerializedName("biography")
    val biography: String? = null,

    @field:SerializedName("also_known_as")
    val akaList: List<String>? = null
)