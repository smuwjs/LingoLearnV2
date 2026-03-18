package com.lingolearn.app.shared.core.data.model

// Stubs - Realm dependency removed to debug build issue
class WordModel {
    var id: String = ""
    var word: String = ""
    var phonetic: String = ""
    var meaning: String = ""
    var example: String = ""
    var audioUrl: String? = null
    var category: String = "CET4"
    var createdAt: Long = System.currentTimeMillis()
}

class LearningProgressModel {
    var id: String = ""
    var wordId: String = ""
    var repetitions: Int = 0
    var easeFactor: Float = 2.5f
    var interval: Int = 0
    var nextReviewDate: Long = System.currentTimeMillis()
    var masteryLevel: Int = 0
    var lastReviewedAt: Long? = null
}

class UserSettingsModel {
    var id: String = ""
    var dailyGoal: Int = 20
    var reminderEnabled: Boolean = false
    var reminderTime: String = "09:00"
    var soundEnabled: Boolean = true
    var vibrationEnabled: Boolean = true
    var autoPlayTts: Boolean = false
    var themeMode: String = "system"
    var currentStreak: Int = 0
    var lastStudyDate: Long? = null
}
