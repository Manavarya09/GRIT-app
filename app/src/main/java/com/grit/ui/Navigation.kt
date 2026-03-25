package com.grit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grit.GritApplication
import com.grit.ui.screens.failures.FailuresScreen
import com.grit.ui.screens.failures.FailuresViewModel
import com.grit.ui.screens.focus.FocusScreen
import com.grit.ui.screens.focus.FocusViewModel
import com.grit.ui.screens.home.HomeScreen
import com.grit.ui.screens.home.HomeViewModel
import com.grit.ui.screens.social.SocialScreen
import com.grit.ui.screens.social.SocialViewModel
import com.grit.ui.screens.stats.StatsScreen
import com.grit.ui.screens.stats.StatsViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Focus : Screen("focus/{taskName}") {
        fun createRoute(taskName: String) = "focus/$taskName"
    }
    data object Stats : Screen("stats")
    data object Failures : Screen("failures")
    data object Social : Screen("social")
}

@Composable
fun GritNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val application = context.applicationContext as GritApplication
    val focusRepository = application.focusRepository
    val socialRepository = application.socialRepository

    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory(focusRepository, socialRepository)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onStartFocus = { taskName ->
                    navController.navigate(Screen.Focus.createRoute(taskName))
                },
                onNavigateToStats = {
                    navController.navigate(Screen.Stats.route)
                },
                onNavigateToFailures = {
                    navController.navigate(Screen.Failures.route)
                },
                onNavigateToSocial = {
                    navController.navigate(Screen.Social.route)
                }
            )
        }

        composable(Screen.Focus.route) { backStackEntry ->
            val taskName = backStackEntry.arguments?.getString("taskName") ?: ""
            val focusViewModel: FocusViewModel = viewModel(
                factory = FocusViewModel.Factory(focusRepository, socialRepository)
            )

            FocusScreen(
                taskName = taskName,
                viewModel = focusViewModel,
                onExit = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Stats.route) {
            val statsViewModel: StatsViewModel = viewModel(
                factory = StatsViewModel.Factory(focusRepository)
            )

            StatsScreen(
                viewModel = statsViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Failures.route) {
            val failuresViewModel: FailuresViewModel = viewModel(
                factory = FailuresViewModel.Factory(focusRepository)
            )

            FailuresScreen(
                viewModel = failuresViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Social.route) {
            val socialViewModel: SocialViewModel = viewModel(
                factory = SocialViewModel.Factory(socialRepository)
            )

            SocialScreen(
                viewModel = socialViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
