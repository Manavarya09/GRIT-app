package com.grit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.grit.data.local.entity.DistractionEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistractionEventDao {
    @Insert
    suspend fun insert(event: DistractionEventEntity): Long

    @Query("SELECT * FROM distraction_events ORDER BY timestamp DESC")
    fun getAllDistractions(): Flow<List<DistractionEventEntity>>

    @Query("SELECT COUNT(*) FROM distraction_events")
    fun getTotalDistractionCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM distraction_events WHERE timestamp >= :startOfDay")
    fun getTodayDistractionCount(startOfDay: Long): Flow<Int>

    @Query("SELECT * FROM distraction_events WHERE sessionId = :sessionId")
    fun getDistractionsForSession(sessionId: Long): Flow<List<DistractionEventEntity>>
}
