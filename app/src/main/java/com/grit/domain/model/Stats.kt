package com.grit.domain.model

data class Stats(
    val totalFocusTimeMinutes: Int = 0,
    val totalSessions: Int = 0,
    val completedSessions: Int = 0,
    val distractionCount: Int = 0,
    val currentStreak: Int = 0,
    val todayFocusTime: Int = 0,
    val todayDistractions: Int = 0
)
