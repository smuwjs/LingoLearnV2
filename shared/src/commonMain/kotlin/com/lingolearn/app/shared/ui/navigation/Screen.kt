package com.lingolearn.app.shared.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    data object Home : Screen(
        route = "home",
        title = "首页",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    data object Learning : Screen(
        route = "learning",
        title = "学习"
    )

    data object Practice : Screen(
        route = "practice",
        title = "练习"
    )

    data object Progress : Screen(
        route = "progress",
        title = "进度",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star
    )

    data object Settings : Screen(
        route = "settings",
        title = "设置",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    companion object {
        val bottomNavItems = listOf(Home, Progress, Settings)
    }
}
