package com.lingolearn.app.shared.feature.practice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lingolearn.app.shared.ui.theme.Error
import com.lingolearn.app.shared.ui.theme.Success

@Composable
fun PracticeScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    questionType: QuestionType = QuestionType.MULTIPLE_CHOICE,
    viewModel: PracticeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading...")
            }
        } else if (uiState.isSessionComplete) {
            PracticeResultsScreen(
                correctCount = uiState.correctCount,
                totalQuestions = uiState.questions.size,
                onFinish = onNavigateBack
            )
        } else {
            // Timer
            val progress = uiState.remainingTimeSeconds.toFloat() / uiState.totalTimeSeconds.toFloat()
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Time: ${uiState.remainingTimeSeconds}s",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Question
            uiState.questions.getOrNull(uiState.currentQuestionIndex)?.let { question ->
                QuestionCard(
                    question = question,
                    selectedAnswer = uiState.selectedAnswer,
                    isCorrect = uiState.isCorrect,
                    onSelectAnswer = { viewModel.selectAnswer(it) },
                    onNext = { viewModel.nextQuestion() }
                )
            }
        }
    }
}

@Composable
private fun QuestionCard(
    question: Question,
    selectedAnswer: String?,
    isCorrect: Boolean?,
    onSelectAnswer: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (question.type) {
                QuestionType.MULTIPLE_CHOICE -> "What does this word mean?"
                QuestionType.FILL_IN_BLANK -> "Type the English word:"
                QuestionType.LISTENING -> "Which word was pronounced?"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Word display
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = question.word.word,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (question.type) {
            QuestionType.MULTIPLE_CHOICE, QuestionType.LISTENING -> {
                // Answer options
                question.options.forEach { option ->
                    AnswerOption(
                        text = option,
                        isSelected = selectedAnswer == option,
                        isCorrect = if (selectedAnswer != null) option == question.correctAnswer else null,
                        onClick = { if (selectedAnswer == null) onSelectAnswer(option) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            QuestionType.FILL_IN_BLANK -> {
                var textAnswer by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = textAnswer,
                    onValueChange = { textAnswer = it },
                    label = { Text("Your answer") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedAnswer == null
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onSelectAnswer(textAnswer) },
                    enabled = textAnswer.isNotBlank() && selectedAnswer == null
                ) {
                    Text("Submit")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Result feedback
        AnimatedVisibility(visible = selectedAnswer != null) {
            Text(
                text = if (isCorrect == true) "Correct!" else "Incorrect",
                color = if (isCorrect == true) Success else Error,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer != null) {
            Button(onClick = onNext) {
                Text("Next")
            }
        }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect == true -> Success.copy(alpha = 0.2f)
        isSelected && isCorrect == false -> Error.copy(alpha = 0.2f)
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun PracticeResultsScreen(
    correctCount: Int,
    totalQuestions: Int,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Practice Complete!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        val percentage = if (totalQuestions > 0) (correctCount * 100) / totalQuestions else 0

        Text(
            text = "$correctCount / $totalQuestions",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "$percentage% Correct",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = onFinish) {
            Text("Finish")
        }
    }
}
