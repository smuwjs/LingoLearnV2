package com.lingolearn.app.shared.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lingolearn.app.shared.core.domain.model.ThemeMode

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSettings()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Daily Goal
        SettingsCard(title = "Learning Goal") {
            Column {
                Text(
                    text = "Daily Goal: ${uiState.dailyGoal} words",
                    style = MaterialTheme.typography.bodyLarge
                )
                Slider(
                    value = uiState.dailyGoal.toFloat(),
                    onValueChange = { viewModel.updateDailyGoal(it.toInt()) },
                    valueRange = 10f..100f,
                    steps = 8
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reminder
        SettingsCard(title = "Daily Reminder") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Reminder",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = uiState.reminderEnabled,
                    onCheckedChange = { viewModel.updateReminder(it) }
                )
            }
            if (uiState.reminderEnabled) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reminder Time: ${uiState.reminderTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sound & Vibration
        SettingsCard(title = "Feedback") {
            SettingsToggleRow(
                title = "Sound Effects",
                isChecked = uiState.soundEnabled,
                onToggle = { viewModel.updateSoundVibration(it, uiState.vibrationEnabled) }
            )
            HorizontalDivider()
            SettingsToggleRow(
                title = "Vibration",
                isChecked = uiState.vibrationEnabled,
                onToggle = { viewModel.updateSoundVibration(uiState.soundEnabled, it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TTS
        SettingsCard(title = "Pronunciation") {
            SettingsToggleRow(
                title = "Auto-play TTS",
                isChecked = uiState.autoPlayTts,
                onToggle = { viewModel.updateAutoPlayTts(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Theme
        SettingsCard(title = "Appearance") {
            ThemeSelector(
                selectedMode = uiState.themeMode,
                onModeSelected = { viewModel.updateThemeMode(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Progress
        SettingsCard(title = "Data") {
            Text(
                text = "Reset all learning progress",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { viewModel.showResetConfirmation() }
            )
        }

        if (uiState.showResetConfirmation) {
            ResetConfirmationDialog(
                onConfirm = { viewModel.resetProgress() },
                onDismiss = { viewModel.dismissResetConfirmation() }
            )
        }
    }
}

@Composable
private fun SettingsCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onToggle
        )
    }
}

@Composable
private fun ThemeSelector(
    selectedMode: ThemeMode,
    onModeSelected: (ThemeMode) -> Unit
) {
    Column {
        ThemeMode.entries.forEach { mode ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onModeSelected(mode) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedMode == mode,
                    onClick = { onModeSelected(mode) }
                )
                Text(
                    text = when (mode) {
                        ThemeMode.SYSTEM -> "System Default"
                        ThemeMode.LIGHT -> "Light"
                        ThemeMode.DARK -> "Dark"
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun ResetConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Reset Progress?") },
        text = {
            Text("This will delete all your learning progress. This action cannot be undone.")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Reset", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
