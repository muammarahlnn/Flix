package com.ardnn.flix.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ardnn.flix.core.data.source.local.entity.CastEntity

@Dao
interface CastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCasts(casts: List<CastEntity>)
}