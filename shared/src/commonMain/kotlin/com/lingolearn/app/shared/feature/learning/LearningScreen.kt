package com.lingolearn.app.shared.feature.learning

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.lingolearn.app.shared.ui.component.FlashCard
import kotlin.math.roundToInt

@Composable
fun LearningScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LearningViewModel = koinViewModel(),
    isReviewMode: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(isReviewMode) {
        viewModel.loadSession(isReviewMode)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        if (uiState.isLoading) {
            Text(
                text = "Loading...",
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (uiState.isSessionComplete) {
            SessionCompleteDialog(
                knownCount = uiState.knownCount,
                unknownCount = uiState.unknownCount,
                onDismiss = onNavigateBack
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress indicator
                Text(
                    text = "${uiState.currentIndex + 1} / ${uiState.totalWords}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Score indicators
                Text(
                    text = "✓ ${uiState.knownCount}  ✗ ${uiState.unknownCount}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Flashcard with swipe
                uiState.currentWord?.let { word ->
                    SwipeableFlashCard(
                        word = word,
                        isFlipped = uiState.isFlipped,
                        onFlip = { viewModel.flipCard() },
                        onSwipeLeft = { viewModel.onSwipe(false) },
                        onSwipeRight = { viewModel.onSwipe(true) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Instructions
                Text(
                    text = "Swipe right if you know, left if you don't",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SwipeableFlashCard(
    word: com.lingolearn.app.shared.core.domain.model.Word,
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val screenWidthPx = LocalConfiguration.current.screenWidthDp * 2

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        when {
                            offsetX > screenWidthPx / 4 -> onSwipeRight()
                            offsetX < -screenWidthPx / 4 -> onSwipeLeft()
                        }
                        offsetX = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                    }
                )
            }
    ) {
        FlashCard(
            word = word,
            isFlipped = isFlipped,
            onFlip = onFlip,
            onSpeak = { /* TTS would be implemented here */ },
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
        )
    }
}

@Composable
private fun SessionCompleteDialog(
    knownCount: Int,
    unknownCount: Int,
    onDismiss: () -> Unit
) {
    val total = knownCount + unknownCount
    val percentage = if (total > 0) (knownCount * 100) / total else 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Session Complete!",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(text = "Correct: $knownCount")
                Text(text = "Incorrect: $unknownCount")
                Text(text = "Accuracy: $percentage%")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Continue")
            }
        }
    )
}
