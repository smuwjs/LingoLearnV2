package com.lingolearn.app.shared.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lingolearn.app.shared.core.data.repository.ProgressRepository
import com.lingolearn.app.shared.core.data.repository.SettingsRepository
import com.lingolearn.app.shared.core.domain.model.ThemeMode
import com.lingolearn.app.shared.core.domain.model.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SettingsUiState(
    val dailyGoal: Int = 20,
    val reminderEnabled: Boolean = false,
    val reminderTime: String = "09:00",
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val autoPlayTts: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val showResetConfirmation: Boolean = false,
    val currentStreak: Int = 0,
    val isLoading: Boolean = true
)

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun loadSettings() {
        viewModelScope.launch {
            val settings = settingsRepository.getSettingsOnce()
            _uiState.value = SettingsUiState(
                dailyGoal = settings.dailyGoal,
                reminderEnabled = settings.reminderEnabled,
                reminderTime = settings.reminderTime,
                soundEnabled = settings.soundEnabled,
                vibrationEnabled = settings.vibrationEnabled,
                autoPlayTts = settings.autoPlayTts,
                themeMode = settings.themeMode,
                isLoading = false
            )
        }
    }

    fun updateDailyGoal(goal: Int) {
        viewModelScope.launch {
            settingsRepository.updateDailyGoal(goal)
            _uiState.value = _uiState.value.copy(dailyGoal = goal)
        }
    }

    fun updateReminder(enabled: Boolean, time: String? = null) {
        viewModelScope.launch {
            settingsRepository.updateReminder(enabled, time ?: _uiState.value.reminderTime)
            _uiState.value = _uiState.value.copy(
                reminderEnabled = enabled,
                reminderTime = time ?: _uiState.value.reminderTime
            )
        }
    }

    fun updateSoundVibration(sound: Boolean, vibration: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSoundVibration(sound, vibration)
            _uiState.value = _uiState.value.copy(
                soundEnabled = sound,
                vibrationEnabled = vibration
            )
        }
    }

    fun updateAutoPlayTts(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateAutoPlayTts(enabled)
            _uiState.value = _uiState.value.copy(autoPlayTts = enabled)
        }
    }

    fun updateThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.updateThemeMode(mode)
            _uiState.value = _uiState.value.copy(themeMode = mode)
        }
    }

    fun showResetConfirmation() {
        _uiState.value = _uiState.value.copy(showResetConfirmation = true)
    }

    fun dismissResetConfirmation() {
        _uiState.value = _uiState.value.copy(showResetConfirmation = false)
    }

    fun resetProgress() {
        viewModelScope.launch {
            progressRepository.resetAllProgress()
            settingsRepository.resetProgress()
            _uiState.value = _uiState.value.copy(
                showResetConfirmation = false,
                dailyGoal = 20,
                reminderEnabled = false,
                currentStreak = 0
            )
        }
    }
}
