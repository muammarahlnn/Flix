package com.ardnn.flix.utils

import android.content.Context
import com.ardnn.flix.data.FilmEntity
import org.json.JSONObject

class FilmsData(private val context: Context) {
    val movies: List<FilmEntity> = getFilmsData("movies.json", "movies")
    val tvShows: List<FilmEntity> = getFilmsData("tvshows.json", "tv_shows")

    private fun getFilmsData(fileName: String, key: String): List<FilmEntity> {
        val filmsData: String =
            context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        val filmArr = JSONObject(filmsData).getJSONArray(key)

        val tempList = ArrayList<FilmEntity>()
        for (i in 0 until filmArr.length()) {
            val data = filmArr.getJSONObject(i)
            val poster = context.resources.getIdentifier(
                data.getString("poster"), "drawable", context.packageName)

            val film = FilmEntity(
                data.getString("title"),
                data.getString("overview"),
                data.getString("release_date"),
                data.getDouble("rating"),
                data.getInt("runtime"),
                poster
            )
            tempList.add(film)
        }
        return tempList
    }
}