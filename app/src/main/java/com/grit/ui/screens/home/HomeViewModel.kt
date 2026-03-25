package com.grit.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.grit.data.repository.FocusRepository
import com.grit.data.repository.SocialRepository
import com.grit.domain.model.Stats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val focusRepository: FocusRepository,
    private val socialRepository: SocialRepository
) : ViewModel() {

    val stats: StateFlow<Stats> = focusRepository.getStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Stats()
        )

    private val _taskName = MutableStateFlow("")
    val taskName: StateFlow<String> = _taskName.asStateFlow()

    fun updateTaskName(name: String) {
        _taskName.value = name
    }

    fun seedSocialData() {
        viewModelScope.launch {
            socialRepository.seedMockData()
        }
    }

    class Factory(
        private val focusRepository: FocusRepository,
        private val socialRepository: SocialRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(focusRepository, socialRepository) as T
        }
    }
}
