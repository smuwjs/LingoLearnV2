package com.lingolearn.app.shared.core.domain.model

data class UserSettings(
    val dailyGoal: Int,
    val reminderEnabled: Boolean,
    val reminderTime: String,
    val soundEnabled: Boolean,
    val vibrationEnabled: Boolean,
    val autoPlayTts: Boolean,
    val themeMode: ThemeMode,
    val currentStreak: Int,
    val lastStudyDate: Long?
)

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}
