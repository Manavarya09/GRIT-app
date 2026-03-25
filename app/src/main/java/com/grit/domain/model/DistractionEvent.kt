package com.grit.domain.model

data class DistractionEvent(
    val id: Long = 0,
    val sessionId: Long,
    val timestamp: Long,
    val reason: String = "User exited focus mode",
    val durationBeforeDistraction: Int = 0
)
