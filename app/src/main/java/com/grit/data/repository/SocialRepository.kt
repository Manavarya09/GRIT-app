package com.grit.data.repository

import com.grit.data.local.dao.SocialActivityDao
import com.grit.data.local.entity.SocialActivityEntity
import com.grit.domain.model.SocialActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SocialRepository(
    private val socialActivityDao: SocialActivityDao
) {
    fun getRecentActivities(): Flow<List<SocialActivity>> =
        socialActivityDao.getRecentActivities().map { list -> list.map { it.toDomain() } }

    suspend fun addActivity(activity: SocialActivity): Long {
        return socialActivityDao.insert(activity.toEntity())
    }

    suspend fun seedMockData() {
        val currentTime = System.currentTimeMillis()
        val mockActivities = listOf(
            SocialActivityEntity(
                userName = "Alex",
                action = "Completed 2h focus",
                duration = 120,
                timestamp = currentTime - 3600000,
                isFailure = false
            ),
            SocialActivityEntity(
                userName = "Sarah",
                action = "Failed after 10 mins",
                duration = 10,
                timestamp = currentTime - 7200000,
                isFailure = true
            ),
            SocialActivityEntity(
                userName = "Mike",
                action = "Completed 45m focus",
                duration = 45,
                timestamp = currentTime - 10800000,
                isFailure = false
            ),
            SocialActivityEntity(
                userName = "Jordan",
                action = "Completed 1h focus",
                duration = 60,
                timestamp = currentTime - 14400000,
                isFailure = false
            ),
            SocialActivityEntity(
                userName = "Casey",
                action = "Failed after 5 mins",
                duration = 5,
                timestamp = currentTime - 18000000,
                isFailure = true
            ),
            SocialActivityEntity(
                userName = "Riley",
                action = "Completed 30m focus",
                duration = 30,
                timestamp = currentTime - 21600000,
                isFailure = false
            ),
            SocialActivityEntity(
                userName = "Taylor",
                action = "Completed 90m focus",
                duration = 90,
                timestamp = currentTime - 28800000,
                isFailure = false
            ),
            SocialActivityEntity(
                userName = "Morgan",
                action = "Failed after 25 mins",
                duration = 25,
                timestamp = currentTime - 36000000,
                isFailure = true
            )
        )
        socialActivityDao.insertAll(mockActivities)
    }

    private fun SocialActivityEntity.toDomain() = SocialActivity(
        id = id,
        userName = userName,
        action = action,
        duration = duration,
        timestamp = timestamp,
        isFailure = isFailure
    )

    private fun SocialActivity.toEntity() = SocialActivityEntity(
        id = id,
        userName = userName,
        action = action,
        duration = duration,
        timestamp = timestamp,
        isFailure = isFailure
    )
}
