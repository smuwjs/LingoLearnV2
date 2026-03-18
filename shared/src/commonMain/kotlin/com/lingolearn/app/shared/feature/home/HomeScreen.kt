package com.lingolearn.app.shared.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lingolearn.app.shared.ui.component.DueReviewBadge
import com.lingolearn.app.shared.ui.component.ProgressRing
import com.lingolearn.app.shared.ui.component.StreakBadge

@Composable
fun HomeScreen(
    onStartLearning: () -> Unit,
    onQuickReview: () -> Unit,
    onRandomTest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LingoLearn",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            StreakBadge(streakCount = uiState.currentStreak)
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Progress Ring
        ProgressRing(
            progress = viewModel.getProgress(),
            completed = uiState.wordsCompletedToday,
            total = uiState.dailyGoal
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Today's Progress",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Due Review Badge
        if (uiState.dueReviewCount > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You have ",
                    style = MaterialTheme.typography.bodyLarge
                )
                DueReviewBadge(count = uiState.dueReviewCount)
                Text(
                    text = " words due for review",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // Quick Action Buttons
        Button(
            onClick = onStartLearning,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "开始学习",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onQuickReview,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "快速复习")
            }

            OutlinedButton(
                onClick = onRandomTest,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "随机测试")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
