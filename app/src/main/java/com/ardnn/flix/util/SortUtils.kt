package com.ardnn.flix.util

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object SortUtils {
    const val MOVIES = "Movies"
    const val TV_SHOWS = "TvShows"
    const val ASCENDING = "Ascending"
    const val DESCENDING = "Descending"
    const val RANDOM = "Random"
    const val DEFAULT = "Default"

    fun getSortedSection(type: String, section: Int, filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder()
        when (type) {
            MOVIES -> {
                simpleQuery.append("SELECT * FROM section_movie_entities WHERE section_id = $section")
            }
            TV_SHOWS -> {
                simpleQuery.append("SELECT * FROM section_tv_show_entities WHERE section_id = $section")
            }
        }
        when (filter) {
            ASCENDING -> {
                simpleQuery.append("ORDER BY title ASC")
            }
            DESCENDING -> {
                simpleQuery.append("ORDER BY title DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
            DEFAULT -> {
                return SimpleSQLiteQuery(simpleQuery.toString())
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}