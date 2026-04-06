package com.grit.data.repository

import com.grit.data.local.dao.DistractionEventDao
import com.grit.data.local.dao.FocusSessionDao
import com.grit.data.local.entity.DistractionEventEntity
import com.grit.data.local.entity.FocusSessionEntity
import com.grit.domain.model.DistractionEvent
import com.grit.domain.model.FocusSession
import com.grit.domain.model.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Calendar

class FocusRepository(
    private val sessionDao: FocusSessionDao,
    private val distractionDao: DistractionEventDao
) {
    fun getAllSessions(): Flow<List<FocusSession>> =
        sessionDao.getAllSessions().map { list -> list.map { it.toDomain() } }

    fun getCompletedSessions(): Flow<List<FocusSession>> =
        sessionDao.getCompletedSessions().map { list -> list.map { it.toDomain() } }

    fun getTotalFocusTime(): Flow<Int> =
        sessionDao.getTotalFocusTime().map { it ?: 0 }

    fun getTodayFocusTime(): Flow<Int> {
        val startOfDay = getStartOfDay()
        return sessionDao.getTodayFocusTime(startOfDay).map { it ?: 0 }
    }

    fun getCompletedSessionCount(): Flow<Int> =
        sessionDao.getCompletedSessionCount()

    fun getTotalDistractionCount(): Flow<Int> =
        distractionDao.getTotalDistractionCount()

    fun getTodayDistractionCount(): Flow<Int> {
        val startOfDay = getStartOfDay()
        return distractionDao.getTodayDistractionCount(startOfDay)
    }

    fun getAllDistractions(): Flow<List<DistractionEvent>> =
        distractionDao.getAllDistractions().map { list -> list.map { it.toDomain() } }

    suspend fun startSession(taskName: String): Long {
        val session = FocusSessionEntity(
            taskName = taskName,
            startTime = System.currentTimeMillis(),
            isActive = true,
            isCompleted = false
        )
        return sessionDao.insert(session)
    }

    suspend fun endSession(sessionId: Long, completed: Boolean) {
        val session = sessionDao.getSessionById(sessionId)
        session?.let {
            val endTime = System.currentTimeMillis()
            val duration = ((endTime - it.startTime) / 60000).toInt()
            val updated = it.copy(
                endTime = endTime,
                durationMinutes = duration,
                isCompleted = completed,
                isActive = false
            )
            sessionDao.update(updated)
        }
    }

    suspend fun recordDistraction(sessionId: Long, duration: Int): Long {
        val event = DistractionEventEntity(
            sessionId = sessionId,
            timestamp = System.currentTimeMillis(),
            reason = "User exited focus mode",
            durationBeforeDistraction = duration
        )
        return distractionDao.insert(event)
    }

    suspend fun getActiveSession(): FocusSession? =
        sessionDao.getActiveSession()?.toDomain()

    fun getStats(): Flow<Stats> = combine(
        getTotalFocusTime(),
        getCompletedSessionCount(),
        getTotalDistractionCount(),
        getTodayFocusTime(),
        getTodayDistractionCount()
    ) { totalTime, completed, distractions, todayTime, todayDistractions ->
        Stats(
            totalFocusTimeMinutes = totalTime,
            totalSessions = completed,
            completedSessions = completed,
            distractionCount = distractions,
            todayFocusTime = todayTime,
            todayDistractions = todayDistractions,
            currentStreak = 0
        )
    }

    private fun getStartOfDay(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun FocusSessionEntity.toDomain() = FocusSession(
        id = id,
        taskName = taskName,
        startTime = startTime,
        endTime = endTime,
        durationMinutes = durationMinutes,
        isCompleted = isCompleted,
        isActive = isActive
    )

    private fun DistractionEventEntity.toDomain() = DistractionEvent(
        id = id,
        sessionId = sessionId,
        timestamp = timestamp,
        reason = reason,
        durationBeforeDistraction = durationBeforeDistraction
    )
}
