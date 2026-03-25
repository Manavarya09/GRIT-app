package com.grit.ui.screens.failures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.grit.data.repository.FocusRepository
import com.grit.domain.model.DistractionEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FailuresViewModel(
    focusRepository: FocusRepository
) : ViewModel() {

    val distractions: StateFlow<List<DistractionEvent>> = focusRepository.getAllDistractions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    class Factory(
        private val focusRepository: FocusRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FailuresViewModel(focusRepository) as T
        }
    }
}
