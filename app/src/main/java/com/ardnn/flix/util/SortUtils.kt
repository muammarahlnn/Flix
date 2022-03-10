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
                simpleQuery.append(
                    "SELECT * FROM section_movie_entities " +
                    "JOIN section_movie_cross_ref ON section_movie_entities.section_id = section_movie_cross_ref.section_id " +
                    "JOIN movie_entities ON section_movie_cross_ref.movie_id = movie_entities.movie_id " +
                    "WHERE section_movie_entities.section_id = $section "
                )
            }
            TV_SHOWS -> {
                simpleQuery.append(
                    "SELECT * FROM section_tv_show_entities " +
                    "JOIN section_tv_show_cross_ref ON section_tv_show_entities.section_id = section_tv_show_cross_ref.section_id " +
                    "JOIN tv_show_entities ON section_tv_show_cross_ref.tv_show_id = tv_show_entities.tv_show_id " +
                    "WHERE section_tv_show_entities.section_id = $section "
                )
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
                simpleQuery.append("ORDER BY popularity DESC")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}