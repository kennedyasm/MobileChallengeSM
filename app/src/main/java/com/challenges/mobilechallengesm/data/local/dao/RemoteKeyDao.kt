package com.challenges.mobilechallengesm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenges.mobilechallengesm.data.local.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys")
    suspend fun getRemoteKey(): List<RemoteKeyEntity>

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()

}