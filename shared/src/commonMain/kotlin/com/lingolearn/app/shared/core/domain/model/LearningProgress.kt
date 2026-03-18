package com.lingolearn.app.shared.core.domain.model

data class LearningProgress(
    val wordId: String,
    val repetitions: Int,
    val easeFactor: Float,
    val interval: Int,
    val nextReviewDate: Long,
    val masteryLevel: Int,
    val lastReviewedAt: Long?
)
