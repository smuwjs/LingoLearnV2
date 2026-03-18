package com.lingolearn.app.shared.core.data.repository

import com.lingolearn.app.shared.core.data.model.UserSettingsModel
import com.lingolearn.app.shared.core.data.model.toDomain
import com.lingolearn.app.shared.core.domain.model.ThemeMode
import com.lingolearn.app.shared.core.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsRepository {
    private var settings = MutableStateFlow(createDefaultSettings())

    fun getSettings(): Flow<UserSettings> = settings

    fun getSettingsOnce(): UserSettings = settings.value

    private fun createDefaultSettings(): UserSettings {
        return UserSettings(
            dailyGoal = 20,
            reminderEnabled = false,
            reminderTime = "09:00",
            soundEnabled = true,
            vibrationEnabled = true,
            autoPlayTts = false,
            themeMode = ThemeMode.SYSTEM,
            currentStreak = 0,
            lastStudyDate = null
        )
    }

    suspend fun updateDailyGoal(goal: Int) {
        settings.value = settings.value.copy(dailyGoal = goal)
    }

    suspend fun updateReminder(enabled: Boolean, time: String?) {
        settings.value = settings.value.copy(
            reminderEnabled = enabled,
            reminderTime = time ?: settings.value.reminderTime
        )
    }

    suspend fun updateSoundVibration(sound: Boolean, vibration: Boolean) {
        settings.value = settings.value.copy(
            soundEnabled = sound,
            vibrationEnabled = vibration
        )
    }

    suspend fun updateAutoPlayTts(enabled: Boolean) {
        settings.value = settings.value.copy(autoPlayTts = enabled)
    }

    suspend fun updateThemeMode(mode: ThemeMode) {
        settings.value = settings.value.copy(themeMode = mode)
    }

    suspend fun updateStreak(streak: Int, lastStudyDate: Long?) {
        settings.value = settings.value.copy(
            currentStreak = streak,
            lastStudyDate = lastStudyDate
        )
    }

    suspend fun resetProgress() {
        settings.value = settings.value.copy(currentStreak = 0, lastStudyDate = null)
    }
}
