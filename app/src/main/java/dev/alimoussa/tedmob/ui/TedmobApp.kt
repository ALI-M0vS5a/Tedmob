package dev.alimoussa.tedmob.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dev.alimoussa.tedmob.MainViewModel
import dev.alimoussa.tedmob.feature.home.HomeRoute
import dev.alimoussa.tedmob.feature.profile.ProfileRoute
import dev.alimoussa.tedmob.navigation.TopLevelDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TedmobApp(
    viewModel: MainViewModel = hiltViewModel(),
    onPostsClick: () -> Unit
) {
    val navController = rememberNavController()
    val currentDestination: NavDestination? =
        navController.currentBackStackEntryAsState().value?.destination
    val destination = TopLevelDestination.values().asList()
    val currentTopLevelDestination: TopLevelDestination? = when (currentDestination?.route) {
        "Home_route" -> TopLevelDestination.HOME
        "Profile_route" -> TopLevelDestination.PROFILE
        else -> null
    }
    val scaffoldState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = scaffoldState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                ) {
                    Text(
                        "Home",
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        scaffoldState.close()
                                    }
                                    navController.navigate("Home_route", navOptions {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    })
                                })
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "Posts",
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    onPostsClick()
                                })
                    )
                }
            }
        },
    ) {
        Scaffold(
            modifier = Modifier,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                BottomBar(
                    destinations = destination,
                    onNavigateToDestination = {
                        val topLevelNavOptions = navOptions {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        when (it) {
                            TopLevelDestination.HOME -> {
                                navController.navigate("Home_route", topLevelNavOptions)
                            }

                            TopLevelDestination.PROFILE -> {
                                navController.navigate("Profile_route", topLevelNavOptions)
                            }
                        }
                    },
                    currentDestination = currentDestination,
                    modifier = Modifier.testTag("SpeakWriteBottomBar"),
                )
            },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                if (currentTopLevelDestination != null) {
                    TopAppBar(
                        title = { Text(text = stringResource(id = currentTopLevelDestination.titleTextId)) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { scaffoldState.open() } }) {
                                Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
                            }
                        }
                    )
                }
                NavHost(
                    navController = navController,
                    startDestination = "Home_route",
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    composable("Home_route") {
                        HomeRoute()
                    }
                    composable("Profile_route") {
                        ProfileRoute()
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    val tint = if (selected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = tint,
                    )
                },
                label = {
                    val textColor = if (selected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Text(
                        text = stringResource(destination.iconTextId),
                        color = textColor,
                    )
                },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false