package com.lingolearn.app.shared

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lingolearn.app.shared.ui.theme.LingoLearnTheme

@Composable
fun App() {
    LingoLearnTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text("LingoLearn V2 - Loading...")
        }
    }
}
