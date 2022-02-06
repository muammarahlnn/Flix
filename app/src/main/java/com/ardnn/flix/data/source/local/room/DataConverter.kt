package com.ardnn.flix.data.source.local.room

import androidx.room.TypeConverter
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromGenreList(value: List<GenreEntity>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGenreList(value: String): List<GenreEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}