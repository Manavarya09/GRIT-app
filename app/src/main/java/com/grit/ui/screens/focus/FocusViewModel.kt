package com.grit.ui.screens.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.grit.data.repository.FocusRepository
import com.grit.data.repository.SocialRepository
import com.grit.domain.model.FocusSession
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FocusUiState(
    val sessionId: Long? = null,
    val taskName: String = "",
    val elapsedSeconds: Int = 0,
    val isActive: Boolean = false,
    val showExitConfirmation: Boolean = false,
    val isCompleted: Boolean = false
)

class FocusViewModel(
    private val focusRepository: FocusRepository,
    private val socialRepository: SocialRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun startSession(taskName: String) {
        viewModelScope.launch {
            val sessionId = focusRepository.startSession(taskName)
            _uiState.value = _uiState.value.copy(
                sessionId = sessionId,
                taskName = taskName,
                isActive = true,
                elapsedSeconds = 0
            )
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.isActive) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    elapsedSeconds = _uiState.value.elapsedSeconds + 1
                )
            }
        }
    }

    fun requestExit() {
        _uiState.value = _uiState.value.copy(showExitConfirmation = true)
    }

    fun dismissExitConfirmation() {
        _uiState.value = _uiState.value.copy(showExitConfirmation = false)
    }

    fun confirmExit() {
        viewModelScope.launch {
            val session = _uiState.value
            session.sessionId?.let { sessionId ->
                focusRepository.endSession(sessionId, completed = false)
                focusRepository.recordDistraction(sessionId, session.elapsedSeconds / 60)
                
                socialRepository.addActivity(
                    com.grit.domain.model.SocialActivity(
                        userName = "YOU",
                        action = "Failed after ${session.elapsedSeconds / 60} mins",
                        duration = session.elapsedSeconds / 60,
                        timestamp = System.currentTimeMillis(),
                        isFailure = true
                    )
                )
            }
            _uiState.value = FocusUiState()
        }
    }

    fun completeSession() {
        viewModelScope.launch {
            val session = _uiState.value
            session.sessionId?.let { sessionId ->
                focusRepository.endSession(sessionId, completed = true)
                
                socialRepository.addActivity(
                    com.grit.domain.model.SocialActivity(
                        userName = "YOU",
                        action = "Completed ${session.elapsedSeconds / 60}m focus",
                        duration = session.elapsedSeconds / 60,
                        timestamp = System.currentTimeMillis(),
                        isFailure = false
                    )
                )
            }
            _uiState.value = _uiState.value.copy(
                isActive = false,
                isCompleted = true
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    class Factory(
        private val focusRepository: FocusRepository,
        private val socialRepository: SocialRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FocusViewModel(focusRepository, socialRepository) as T
        }
    }
}
