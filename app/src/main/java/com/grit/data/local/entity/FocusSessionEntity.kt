package com.grit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskName: String,
    val startTime: Long,
    val endTime: Long? = null,
    val durationMinutes: Int = 0,
    val isCompleted: Boolean = false,
    val isActive: Boolean = true
)
