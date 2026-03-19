package com.lingolearn.app.shared

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lingolearn.app.shared.feature.home.HomeScreen
import com.lingolearn.app.shared.feature.learning.LearningScreen
import com.lingolearn.app.shared.feature.practice.PracticeScreen
import com.lingolearn.app.shared.feature.progress.ProgressScreen
import com.lingolearn.app.shared.feature.settings.SettingsScreen
import com.lingolearn.app.shared.ui.navigation.Screen
import com.lingolearn.app.shared.ui.theme.LingoLearnTheme

@Composable
fun App() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    LingoLearnTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            bottomBar = {
                NavigationBar {
                    Screen.bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = {
                                val icon = if (selected) screen.selectedIcon else screen.unselectedIcon
                                icon?.let { Icon(it, contentDescription = screen.title) }
                            },
                            label = { Text(screen.title) },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            onStartLearning = { navController.navigate(Screen.Learning.route) },
                            onQuickReview = { navController.navigate(Screen.Practice.route) },
                            onRandomTest = { navController.navigate(Screen.Practice.route) }
                        )
                    }
                    composable(Screen.Learning.route) {
                        LearningScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable(Screen.Practice.route) {
                        PracticeScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable(Screen.Progress.route) {
                        ProgressScreen()
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen()
                    }
                }
            }
        }
    }
}
