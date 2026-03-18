package com.lingolearn.app.shared.feature.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lingolearn.app.shared.core.algorithm.SM2Calculator
import com.lingolearn.app.shared.core.data.repository.ProgressRepository
import com.lingolearn.app.shared.core.data.repository.WordRepository
import com.lingolearn.app.shared.core.domain.model.LearningProgress
import com.lingolearn.app.shared.core.domain.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LearningUiState(
    val currentWord: Word? = null,
    val currentIndex: Int = 0,
    val totalWords: Int = 0,
    val isFlipped: Boolean = false,
    val knownCount: Int = 0,
    val unknownCount: Int = 0,
    val isSessionComplete: Boolean = false,
    val isLoading: Boolean = true
)

class LearningViewModel(
    private val wordRepository: WordRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearningUiState())
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    private var wordList: List<Word> = emptyList()
    private var progressMap: Map<String, LearningProgress> = emptyMap()

    fun loadSession(isReviewMode: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val allWords = wordRepository.getAllWords().first()
            val allProgress = progressRepository.getAllProgress().first()
            progressMap = allProgress.associateBy { it.wordId }

            wordList = if (isReviewMode) {
                // Only due words
                val now = System.currentTimeMillis()
                allWords.filter { word ->
                    val progress = progressMap[word.id]
                    progress == null || progress.nextReviewDate <= now
                }
            } else {
                // New words first, then due words
                val newWords = allWords.filter { progressMap[it.id] == null }
                val dueWords = allWords.filter { word ->
                    val progress = progressMap[word.id]
                    progress != null && progress.nextReviewDate <= System.currentTimeMillis()
                }
                newWords + dueWords
            }.take(20) // Limit session to 20 words

            if (wordList.isNotEmpty()) {
                _uiState.value = LearningUiState(
                    currentWord = wordList[0],
                    currentIndex = 0,
                    totalWords = wordList.size,
                    isFlipped = false,
                    knownCount = 0,
                    unknownCount = 0,
                    isSessionComplete = false,
                    isLoading = false
                )
            } else {
                _uiState.value = LearningUiState(
                    currentWord = null,
                    isSessionComplete = true,
                    isLoading = false
                )
            }
        }
    }

    fun flipCard() {
        _uiState.value = _uiState.value.copy(isFlipped = !_uiState.value.isFlipped)
    }

    fun onSwipe(isRightSwipe: Boolean) {
        viewModelScope.launch {
            val currentWord = _uiState.value.currentWord ?: return@launch
            val currentProgress = progressMap[currentWord.id]
            val quality = SM2Calculator.getQualityFromSwipe(isRightSwipe)

            val newProgress = SM2Calculator.calculateNextReview(currentProgress, quality)
                .copy(wordId = currentWord.id)

            progressRepository.updateProgress(newProgress)
            progressMap = progressMap + (currentWord.id to newProgress)

            _uiState.value = _uiState.value.copy(
                knownCount = if (isRightSwipe) _uiState.value.knownCount + 1 else _uiState.value.knownCount,
                unknownCount = if (!isRightSwipe) _uiState.value.unknownCount + 1 else _uiState.value.unknownCount
            )

            moveToNextWord()
        }
    }

    private fun moveToNextWord() {
        val nextIndex = _uiState.value.currentIndex + 1
        if (nextIndex < wordList.size) {
            _uiState.value = _uiState.value.copy(
                currentWord = wordList[nextIndex],
                currentIndex = nextIndex,
                isFlipped = false
            )
        } else {
            _uiState.value = _uiState.value.copy(isSessionComplete = true)
        }
    }
}
