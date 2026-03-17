package com.newsapp.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.newsapp.presentation.ui.bookmark.BookmarkScreen
import com.newsapp.presentation.ui.detail.DetailScreen
import com.newsapp.presentation.ui.home.HomeScreen
import com.newsapp.presentation.ui.home.SearchScreen
import java.net.URLDecoder
import java.net.URLEncoder

// ─── Routes ───────────────────────────────────────────────────────────────────

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmarks : Screen("bookmarks")
    object Detail : Screen("detail/{articleUrl}") {
        fun createRoute(url: String): String {
            val encoded = URLEncoder.encode(url, "UTF-8")
            return "detail/$encoded"
        }
    }
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(Screen.Search, "Search", Icons.Filled.Search, Icons.Outlined.Search),
    BottomNavItem(Screen.Bookmarks, "Saved", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder)
)

// ─── Nav Graph ────────────────────────────────────────────────────────────────

@Composable
fun NewsNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Bookmarks.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NewsBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { screen ->
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(tween(300)) { it / 4 } + fadeIn(tween(300))
            },
            exitTransition = {
                slideOutHorizontally(tween(300)) { -it / 4 } + fadeOut(tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(tween(300)) { -it / 4 } + fadeIn(tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(tween(300)) { it / 4 } + fadeOut(tween(300))
            }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onArticleClick = { url ->
                        navController.navigate(Screen.Detail.createRoute(url))
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    onArticleClick = { url ->
                        navController.navigate(Screen.Detail.createRoute(url))
                    }
                )
            }
            composable(Screen.Bookmarks.route) {
                BookmarkScreen(
                    onArticleClick = { url ->
                        navController.navigate(Screen.Detail.createRoute(url))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("articleUrl") { type = NavType.StringType })
            ) { backStackEntry ->
                val encodedUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
                val decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8")
                DetailScreen(
                    articleUrl = decodedUrl,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun NewsBottomNavBar(
    currentRoute: String?,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
