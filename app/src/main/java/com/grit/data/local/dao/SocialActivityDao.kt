package com.grit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.grit.data.local.entity.SocialActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SocialActivityDao {
    @Insert
    suspend fun insert(activity: SocialActivityEntity): Long

    @Insert
    suspend fun insertAll(activities: List<SocialActivityEntity>)

    @Query("SELECT * FROM social_activities ORDER BY timestamp DESC LIMIT 50")
    fun getRecentActivities(): Flow<List<SocialActivityEntity>>

    @Query("DELETE FROM social_activities")
    suspend fun deleteAll()
}
