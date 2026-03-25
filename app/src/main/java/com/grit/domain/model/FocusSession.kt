package com.grit.domain.model

data class FocusSession(
    val id: Long = 0,
    val taskName: String,
    val startTime: Long,
    val endTime: Long? = null,
    val durationMinutes: Int = 0,
    val isCompleted: Boolean = false,
    val isActive: Boolean = true
)
