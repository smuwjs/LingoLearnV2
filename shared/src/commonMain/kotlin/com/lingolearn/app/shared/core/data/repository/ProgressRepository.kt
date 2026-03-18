package com.lingolearn.app.shared.core.data.repository

import com.lingolearn.app.shared.core.data.model.toDomain
import com.lingolearn.app.shared.core.domain.model.LearningProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ProgressRepository {
    private val progress = MutableStateFlow<List<LearningProgress>>(emptyList())

    fun getAllProgress(): Flow<List<LearningProgress>> = progress

    fun getProgressForWord(wordId: String): LearningProgress? {
        return progress.value.find { it.wordId == wordId }
    }

    fun getDueWords(currentTime: Long): Flow<List<LearningProgress>> {
        return progress.map { list -> list.filter { it.nextReviewDate <= currentTime } }
    }

    suspend fun updateProgress(newProgress: LearningProgress) {
        val current = progress.value.toMutableList()
        val index = current.indexOfFirst { it.wordId == newProgress.wordId }
        if (index >= 0) {
            current[index] = newProgress
        } else {
            current.add(newProgress)
        }
        progress.value = current
    }

    suspend fun resetAllProgress() {
        progress.value = emptyList()
    }

    fun getWordsLearnedToday(todayStart: Long, todayEnd: Long): List<LearningProgress> {
        return progress.value.filter { p ->
            p.lastReviewedAt?.let { it >= todayStart && it <= todayEnd } ?: false
        }
    }

    fun getStreakData(lastNDays: Int): Map<Long, Int> {
        val result = mutableMapOf<Long, Int>()
        val now = System.currentTimeMillis()
        val dayMillis = 24 * 60 * 60 * 1000L

        for (i in 0 until lastNDays) {
            val dayStart = now - (i * dayMillis)
            val dayStartNormalized = dayStart - (dayStart % dayMillis)
            val dayEnd = dayStartNormalized + dayMillis

            val count = progress.value.count { p ->
                p.lastReviewedAt?.let { it >= dayStartNormalized && it < dayEnd } ?: false
            }

            result[dayStartNormalized] = count
        }
        return result
    }
}
