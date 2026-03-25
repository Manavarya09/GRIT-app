package com.grit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "social_activities")
data class SocialActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val action: String,
    val duration: Int = 0,
    val timestamp: Long,
    val isFailure: Boolean = false
)
