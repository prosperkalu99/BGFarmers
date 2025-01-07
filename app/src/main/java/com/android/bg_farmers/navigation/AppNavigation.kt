package com.android.bg_farmers.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.bg_farmers.navigation.destinations.listComposable
import com.android.bg_farmers.navigation.destinations.splashComposable
import com.android.bg_farmers.navigation.destinations.farmerComposable
import com.android.bg_farmers.ui.viewmodels.SharedViewModel
import com.android.bg_farmers.util.Constants.SPLASH_SCREEN

@Composable
fun AppNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
        farmerComposable(
            navigateToListScreen = screen.farmer,
            sharedViewModel = sharedViewModel
        )
    }

}