package com.lingolearn.app.shared.core.data.model

import com.lingolearn.app.shared.core.domain.model.LearningProgress
import com.lingolearn.app.shared.core.domain.model.UserSettings
import com.lingolearn.app.shared.core.domain.model.Word
import com.lingolearn.app.shared.core.domain.model.WordCategory

fun WordModel.toDomain(): Word {
    return Word(
        id = id,
        word = word,
        phonetic = phonetic,
        meaning = meaning,
        example = example,
        audioUrl = audioUrl,
        category = WordCategory.valueOf(category.ifEmpty { "CET4" }),
        createdAt = createdAt
    )
}

fun LearningProgressModel.toDomain(): LearningProgress {
    return LearningProgress(
        wordId = wordId,
        repetitions = repetitions,
        easeFactor = easeFactor,
        interval = interval,
        nextReviewDate = nextReviewDate,
        masteryLevel = masteryLevel,
        lastReviewedAt = lastReviewedAt
    )
}

fun UserSettingsModel.toDomain(): UserSettings {
    return UserSettings(
        dailyGoal = dailyGoal,
        reminderEnabled = reminderEnabled,
        reminderTime = reminderTime,
        soundEnabled = soundEnabled,
        vibrationEnabled = vibrationEnabled,
        autoPlayTts = autoPlayTts,
        themeMode = com.lingolearn.app.shared.core.domain.model.ThemeMode.valueOf(
            themeMode.ifEmpty { "SYSTEM" }
        ),
        currentStreak = currentStreak,
        lastStudyDate = lastStudyDate
    )
}
