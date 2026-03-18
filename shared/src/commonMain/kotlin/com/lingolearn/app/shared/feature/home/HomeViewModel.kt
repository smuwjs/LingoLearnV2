package com.lingolearn.app.shared.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lingolearn.app.shared.core.data.repository.ProgressRepository
import com.lingolearn.app.shared.core.data.repository.SettingsRepository
import com.lingolearn.app.shared.core.data.repository.WordRepository
import com.lingolearn.app.shared.core.domain.model.LearningProgress
import com.lingolearn.app.shared.core.domain.model.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val dailyGoal: Int = 20,
    val wordsCompletedToday: Int = 0,
    val currentStreak: Int = 0,
    val dueReviewCount: Int = 0,
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val wordRepository: WordRepository,
    private val progressRepository: ProgressRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val settings = settingsRepository.getSettingsOnce()
            val now = System.currentTimeMillis()
            val dayMillis = 24 * 60 * 60 * 1000L
            val todayStart = now - (now % dayMillis)
            val todayEnd = todayStart + dayMillis

            val wordsLearnedToday = progressRepository.getWordsLearnedToday(todayStart, todayEnd).size
            val dueWords = progressRepository.getDueWords(now).first()

            _uiState.value = HomeUiState(
                dailyGoal = settings.dailyGoal,
                wordsCompletedToday = wordsLearnedToday,
                currentStreak = settings.currentStreak,
                dueReviewCount = dueWords.size,
                isLoading = false
            )
        }
    }

    fun getProgress(): Float {
        val state = _uiState.value
        return if (state.dailyGoal > 0) {
            state.wordsCompletedToday.toFloat() / state.dailyGoal.toFloat()
        } else 0f
    }
}
