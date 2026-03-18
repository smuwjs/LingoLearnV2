package com.lingolearn.app.shared.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lingolearn.app.shared.core.data.repository.ProgressRepository
import com.lingolearn.app.shared.core.data.repository.WordRepository
import com.lingolearn.app.shared.core.domain.model.LearningProgress
import com.lingolearn.app.shared.core.domain.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val unlockedAt: Long? = null
)

data class ProgressUiState(
    val wordsLearnedToday: Int = 0,
    val totalWordsLearned: Int = 0,
    val weeklyData: Map<Long, Int> = emptyMap(), // date to count
    val masteryDistribution: Map<String, Int> = emptyMap(), // level to count
    val achievements: List<Achievement> = emptyList(),
    val isLoading: Boolean = true
)

class ProgressViewModel(
    private val wordRepository: WordRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val allWords = wordRepository.getAllWords().first()
            val allProgress = progressRepository.getAllProgress().first()
            val weeklyData = progressRepository.getStreakData(7)

            val masteryDist = mutableMapOf(
                "New" to 0,
                "Learning" to 0,
                "Familiar" to 0,
                "Mastered" to 0
            )

            allWords.forEach { word ->
                val progress = allProgress.find { it.wordId == word.id }
                when {
                    progress == null -> masteryDist["New"] = masteryDist["New"]!! + 1
                    progress.masteryLevel <= 2 -> masteryDist["Learning"] = masteryDist["Learning"]!! + 1
                    progress.masteryLevel <= 4 -> masteryDist["Familiar"] = masteryDist["Familiar"]!! + 1
                    else -> masteryDist["Mastered"] = masteryDist["Mastered"]!! + 1
                }
            }

            val achievements = listOf(
                Achievement("first_word", "First Step", "Learn your first word", allProgress.isNotEmpty()),
                Achievement("streak_7", "Week Warrior", "Maintain a 7-day streak", weeklyData.values.sum() >= 7),
                Achievement("streak_30", "Month Master", "Maintain a 30-day streak", weeklyData.values.sum() >= 30),
                Achievement("words_100", "Century", "Learn 100 words", allProgress.size >= 100),
                Achievement("words_500", "Half Thousand", "Learn 500 words", allProgress.size >= 500)
            )

            val now = System.currentTimeMillis()
            val dayMillis = 24 * 60 * 60 * 1000L
            val todayStart = now - (now % dayMillis)
            val todayEnd = todayStart + dayMillis
            val wordsToday = progressRepository.getWordsLearnedToday(todayStart, todayEnd).size

            _uiState.value = ProgressUiState(
                wordsLearnedToday = wordsToday,
                totalWordsLearned = allProgress.size,
                weeklyData = weeklyData,
                masteryDistribution = masteryDist,
                achievements = achievements,
                isLoading = false
            )
        }
    }
}
