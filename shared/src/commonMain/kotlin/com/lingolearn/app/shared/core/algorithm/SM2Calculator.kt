package com.lingolearn.app.shared.core.algorithm

import com.lingolearn.app.shared.core.domain.model.LearningProgress
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * SuperMemo 2 (SM-2) Spaced Repetition Algorithm
 *
 * The algorithm calculates the optimal interval for reviewing a flashcard
 * based on how well the user remembers the card.
 *
 * Quality ratings:
 * 0 - Complete blackout, no memory
 * 1 - Incorrect response, but upon seeing the answer it was recognized
 * 2 - Incorrect response, but the correct answer seemed easy to recall
 * 3 - Correct response with serious difficulty
 * 4 - Correct response with some hesitation
 * 5 - Perfect response
 */
object SM2Calculator {

    /**
     * Calculate the next review parameters based on quality rating
     *
     * @param currentProgress Current learning progress (null for new cards)
     * @param quality Rating from 0-5
     * @return Updated learning progress
     */
    fun calculateNextReview(
        currentProgress: LearningProgress?,
        quality: Int
    ): LearningProgress {
        val q = quality.coerceIn(0, 5)

        return if (currentProgress == null) {
            // New card
            val interval = if (q < 3) 1 else 1
            val easeFactor = calculateNewEaseFactor(2.5f, q)
            val masteryLevel = if (q < 3) 0 else 1

            LearningProgress(
                wordId = currentProgress?.wordId ?: "",
                repetitions = if (q < 3) 0 else 1,
                easeFactor = easeFactor,
                interval = interval,
                nextReviewDate = System.currentTimeMillis() + (interval * DAY_MILLIS),
                masteryLevel = masteryLevel,
                lastReviewedAt = System.currentTimeMillis()
            )
        } else {
            // Existing card
            val newEaseFactor = calculateNewEaseFactor(currentProgress.easeFactor, q)
            val (newRepetitions, newInterval) = if (q < 3) {
                // Failed - reset
                0 to 1
            } else {
                // Passed - increase interval
                val newReps = currentProgress.repetitions + 1
                val interval = when (newReps) {
                    1 -> 1
                    2 -> 6
                    else -> (currentProgress.interval * newEaseFactor).roundToInt()
                }
                newReps to interval
            }

            val masteryLevel = calculateMasteryLevel(newRepetitions, q)

            LearningProgress(
                wordId = currentProgress.wordId,
                repetitions = newRepetitions,
                easeFactor = newEaseFactor,
                interval = newInterval,
                nextReviewDate = System.currentTimeMillis() + (newInterval * DAY_MILLIS),
                masteryLevel = masteryLevel,
                lastReviewedAt = System.currentTimeMillis()
            )
        }
    }

    /**
     * Calculate new ease factor using SM-2 formula
     * EF' = EF + (0.1 - (5-q) * (0.08 + (5-q) * 0.02))
     */
    private fun calculateNewEaseFactor(currentEF: Float, quality: Int): Float {
        val newEF = currentEF + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f))
        return max(1.3f, newEF) // Minimum EF is 1.3
    }

    /**
     * Calculate mastery level (0-5) based on repetitions and quality
     */
    private fun calculateMasteryLevel(repetitions: Int, quality: Int): Int {
        return when {
            repetitions == 0 -> 0
            quality < 3 -> max(0, (repetitions - 1) / 2)
            repetitions == 1 -> 1
            repetitions == 2 -> 2
            repetitions <= 4 -> 3
            repetitions <= 6 -> 4
            else -> 5
        }
    }

    /**
     * Filter words that are due for review
     */
    fun filterDueWords(
        words: List<Pair<String, LearningProgress?>>,
        currentTime: Long = System.currentTimeMillis()
    ): List<String> {
        return words.filter { (_, progress) ->
            progress == null || progress.nextReviewDate <= currentTime
        }.map { (wordId, _) -> wordId }
    }

    /**
     * Get quality rating from swipe direction and confidence
     * Right swipe (know) = quality 3-5
     * Left swipe (don't know) = quality 0-2
     */
    fun getQualityFromSwipe(isRightSwipe: Boolean, highConfidence: Boolean = false): Int {
        return if (isRightSwipe) {
            if (highConfidence) 5 else 4
        } else {
            if (highConfidence) 1 else 0
        }
    }

    const val DAY_MILLIS = 24 * 60 * 60 * 1000L
}
