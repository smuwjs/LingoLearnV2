package com.lingolearn.app.shared.core.algorithm

import com.lingolearn.app.shared.core.domain.model.LearningProgress
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class SM2CalculatorTest : DescribeSpec({

    describe("SM2Calculator") {

        describe("calculateNextReview for new cards") {

            it("should reset card on quality < 3") {
                val result = SM2Calculator.calculateNextReview(null, 2)

                result.repetitions shouldBe 0
                result.interval shouldBe 1
                result.masteryLevel shouldBe 0
            }

            it("should advance card on quality >= 3 for new cards") {
                val result = SM2Calculator.calculateNextReview(null, 3)

                result.repetitions shouldBe 1
                result.interval shouldBe 1
                result.masteryLevel shouldBe 1
            }

            it("should calculate ease factor for perfect response") {
                val result = SM2Calculator.calculateNextReview(null, 5)

                // New card with quality 5 should have ease factor > 2.5
                result.easeFactor shouldNotBe 2.5f
            }
        }

        describe("calculateNextReview for existing cards") {

            val baseProgress = LearningProgress(
                wordId = "word1",
                repetitions = 1,
                easeFactor = 2.5f,
                interval = 1,
                nextReviewDate = System.currentTimeMillis(),
                masteryLevel = 1,
                lastReviewedAt = System.currentTimeMillis()
            )

            it("should reset on failed response (quality < 3)") {
                val result = SM2Calculator.calculateNextReview(baseProgress, 2)

                result.repetitions shouldBe 0
                result.interval shouldBe 1
            }

            it("should increase interval on successful response") {
                val result = SM2Calculator.calculateNextReview(baseProgress, 4)

                result.repetitions shouldBe 2
                result.interval shouldBe 6
            }

            it("should use exponential interval after repetition 2") {
                val progress = baseProgress.copy(
                    repetitions = 2,
                    easeFactor = 2.5f,
                    interval = 6
                )
                val result = SM2Calculator.calculateNextReview(progress, 4)

                // Interval should be 6 * 2.5 = 15
                result.interval shouldBe 15
            }

            it("should maintain minimum ease factor of 1.3") {
                val weakProgress = baseProgress.copy(easeFactor = 1.3f)
                val result = SM2Calculator.calculateNextReview(weakProgress, 0)

                // Ease factor should not go below 1.3
                result.easeFactor shouldBe 1.3f.plusOrMinus(0.01f)
            }

            it("should decrease ease factor on poor response") {
                val result = SM2Calculator.calculateNextReview(baseProgress, 1)

                // Ease factor should decrease from 2.5
                result.easeFactor shouldNotBe 2.5f
            }
        }

        describe("filterDueWords") {

            it("should include new cards (null progress)") {
                val words = listOf(
                    "word1" to null,
                    "word2" to null
                )

                val dueWords = SM2Calculator.filterDueWords(words)

                dueWords.size shouldBe 2
            }

            it("should include cards with past review date") {
                val pastDate = System.currentTimeMillis() - (2 * SM2Calculator.DAY_MILLIS)
                val words = listOf(
                    "word1" to LearningProgress(
                        wordId = "word1",
                        repetitions = 1,
                        easeFactor = 2.5f,
                        interval = 1,
                        nextReviewDate = pastDate,
                        masteryLevel = 1,
                        lastReviewedAt = pastDate
                    )
                )

                val dueWords = SM2Calculator.filterDueWords(words)

                dueWords.size shouldBe 1
            }

            it("should exclude cards with future review date") {
                val futureDate = System.currentTimeMillis() + SM2Calculator.DAY_MILLIS
                val words = listOf(
                    "word1" to LearningProgress(
                        wordId = "word1",
                        repetitions = 1,
                        easeFactor = 2.5f,
                        interval = 1,
                        nextReviewDate = futureDate,
                        masteryLevel = 1,
                        lastReviewedAt = System.currentTimeMillis()
                    )
                )

                val dueWords = SM2Calculator.filterDueWords(words)

                dueWords.size shouldBe 0
            }
        }

        describe("getQualityFromSwipe") {

            it("should return quality 5 for right swipe with high confidence") {
                val quality = SM2Calculator.getQualityFromSwipe(isRightSwipe = true, highConfidence = true)

                quality shouldBe 5
            }

            it("should return quality 4 for right swipe with low confidence") {
                val quality = SM2Calculator.getQualityFromSwipe(isRightSwipe = true, highConfidence = false)

                quality shouldBe 4
            }

            it("should return quality 1 for left swipe with high confidence") {
                val quality = SM2Calculator.getQualityFromSwipe(isRightSwipe = false, highConfidence = true)

                quality shouldBe 1
            }

            it("should return quality 0 for left swipe with low confidence") {
                val quality = SM2Calculator.getQualityFromSwipe(isRightSwipe = false, highConfidence = false)

                quality shouldBe 0
            }
        }
    }
})
