package com.challenges.mobilechallengesm.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenges.mobilechallengesm.data.local.entities.BeerEntity

@Dao
interface BeersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM beers")
    fun pagingSource(): PagingSource<Int, BeerEntity>

    @Query("DELETE FROM beers")
    suspend fun clearAll()

}