package com.grit.domain.model

data class SocialActivity(
    val id: Long = 0,
    val userName: String,
    val action: String,
    val duration: Int = 0,
    val timestamp: Long,
    val isFailure: Boolean = false
)
