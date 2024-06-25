package dev.alimoussa.tedmob.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import dev.alimoussa.tedmob.R

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home_screen_title,
        titleTextId = R.string.home_screen_title,
    ),
    PROFILE(
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = R.string.profile_screen_title,
        titleTextId = R.string.profile_screen_title,
    ),
}