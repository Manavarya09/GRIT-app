package com.grit.ui.screens.social

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.grit.data.repository.SocialRepository
import com.grit.domain.model.SocialActivity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SocialViewModel(
    socialRepository: SocialRepository
) : ViewModel() {

    val activities: StateFlow<List<SocialActivity>> = socialRepository.getRecentActivities()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    class Factory(
        private val socialRepository: SocialRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SocialViewModel(socialRepository) as T
        }
    }
}
