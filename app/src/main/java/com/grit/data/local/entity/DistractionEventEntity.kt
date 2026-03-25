package com.grit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "distraction_events")
data class DistractionEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    val timestamp: Long,
    val reason: String = "User exited focus mode",
    val durationBeforeDistraction: Int = 0
)
