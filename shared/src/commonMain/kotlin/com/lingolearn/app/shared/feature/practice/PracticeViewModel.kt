package com.lingolearn.app.shared.feature.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lingolearn.app.shared.core.data.repository.WordRepository
import com.lingolearn.app.shared.core.domain.model.Word
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class QuestionType {
    MULTIPLE_CHOICE,
    FILL_IN_BLANK,
    LISTENING
}

data class Question(
    val word: Word,
    val type: QuestionType,
    val options: List<String> = emptyList(),
    val correctAnswer: String = ""
)

data class PracticeUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val isCorrect: Boolean? = null,
    val correctCount: Int = 0,
    val totalTimeSeconds: Int = 60,
    val remainingTimeSeconds: Int = 60,
    val isSessionComplete: Boolean = false,
    val isLoading: Boolean = true
)

class PracticeViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()

    private var timerJob: kotlinx.coroutines.Job? = null

    fun loadSession(questionType: QuestionType = QuestionType.MULTIPLE_CHOICE) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val allWords = wordRepository.getAllWords().first().shuffled().take(10)
            val questions = generateQuestions(allWords, questionType)

            _uiState.value = PracticeUiState(
                questions = questions,
                currentQuestionIndex = 0,
                totalTimeSeconds = questions.size * 10,
                remainingTimeSeconds = questions.size * 10,
                isLoading = false
            )

            startTimer()
        }
    }

    private fun generateQuestions(words: List<Word>, type: QuestionType): List<Question> {
        return words.map { word ->
            when (type) {
                QuestionType.MULTIPLE_CHOICE -> {
                    val wrongOptions = words
                        .filter { it.id != word.id }
                        .shuffled()
                        .take(3)
                        .map { it.meaning }
                    Question(
                        word = word,
                        type = type,
                        options = (listOf(word.meaning) + wrongOptions).shuffled(),
                        correctAnswer = word.meaning
                    )
                }
                QuestionType.FILL_IN_BLANK -> {
                    Question(
                        word = word,
                        type = type,
                        correctAnswer = word.word
                    )
                }
                QuestionType.LISTENING -> {
                    val wrongOptions = words
                        .filter { it.id != word.id }
                        .shuffled()
                        .take(3)
                        .map { it.word }
                    Question(
                        word = word,
                        type = type,
                        options = (listOf(word.word) + wrongOptions).shuffled(),
                        correctAnswer = word.word
                    )
                }
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingTimeSeconds > 0 && !_uiState.value.isSessionComplete) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    remainingTimeSeconds = _uiState.value.remainingTimeSeconds - 1
                )
            }
            if (_uiState.value.remainingTimeSeconds <= 0) {
                _uiState.value = _uiState.value.copy(isSessionComplete = true)
            }
        }
    }

    fun selectAnswer(answer: String) {
        val currentQuestion = _uiState.value.questions.getOrNull(_uiState.value.currentQuestionIndex) ?: return
        val isCorrect = answer == currentQuestion.correctAnswer

        _uiState.value = _uiState.value.copy(
            selectedAnswer = answer,
            isCorrect = isCorrect,
            correctCount = if (isCorrect) _uiState.value.correctCount + 1 else _uiState.value.correctCount
        )
    }

    fun nextQuestion() {
        val nextIndex = _uiState.value.currentQuestionIndex + 1
        if (nextIndex < _uiState.value.questions.size) {
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = nextIndex,
                selectedAnswer = null,
                isCorrect = null
            )
        } else {
            timerJob?.cancel()
            _uiState.value = _uiState.value.copy(isSessionComplete = true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
