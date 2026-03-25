package com.grit.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.grit.data.repository.FocusRepository
import com.grit.domain.model.Stats
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class StatsViewModel(
    focusRepository: FocusRepository
) : ViewModel() {

    val stats: StateFlow<Stats> = focusRepository.getStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Stats()
        )

    class Factory(
        private val focusRepository: FocusRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StatsViewModel(focusRepository) as T
        }
    }
}
