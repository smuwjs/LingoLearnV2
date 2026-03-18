package com.lingolearn.app.shared.core.algorithm

/**
 * Quality rating for SM-2 algorithm based on user response quality
 */
enum class QualityRating(val value: Int) {
    COMPLETE_BLACKOUT(0),      // No memory at all
    INCORRECT_RECOGNIZED(1),   // Incorrect but recognized answer
    INCORRECT_EASY(2),         // Incorrect but seemed easy after
    DIFFICULT_CORRECT(3),      // Correct with serious difficulty
    HESITANT_CORRECT(4),       // Correct with some hesitation
    PERFECT(5);                // Perfect response

    companion object {
        fun fromSwipe(isRightSwipe: Boolean, highConfidence: Boolean = false): QualityRating {
            return if (isRightSwipe) {
                if (highConfidence) PERFECT else HESITANT_CORRECT
            } else {
                if (highConfidence) INCORRECT_RECOGNIZED else COMPLETE_BLACKOUT
            }
        }

        fun fromValue(value: Int): QualityRating {
            return entries.find { it.value == value } ?: HESITANT_CORRECT
        }
    }
}

/**
 * Swipe direction for flashcard interaction
 */
enum class SwipeDirection {
    LEFT,   // Don't know
    RIGHT,  // Know
    UP      // Favorite
}
