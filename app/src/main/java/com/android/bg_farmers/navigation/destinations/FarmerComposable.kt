package com.android.bg_farmers.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.bg_farmers.ui.screens.farmer.FarmerScreen
import com.android.bg_farmers.ui.viewmodels.SharedViewModel
import com.android.bg_farmers.util.Action
import com.android.bg_farmers.util.Constants.FARMER_ARGUMENT_KEY
import com.android.bg_farmers.util.Constants.FARMER_SCREEN

fun NavGraphBuilder.farmerComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = FARMER_SCREEN,
        arguments = listOf(navArgument(FARMER_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val farmerId = navBackStackEntry.arguments!!.getInt(FARMER_ARGUMENT_KEY)
        LaunchedEffect(key1 = farmerId) {
            sharedViewModel.getSelectedFarmer(farmerId = farmerId)
        }
        val selectedFarmer by sharedViewModel.selectedFarmer.collectAsState()
        LaunchedEffect(key1 = selectedFarmer) {
            if (selectedFarmer != null || farmerId == -1) {
                sharedViewModel.updateFarmerFields(selectedFarmer = selectedFarmer)
            }
        }
        FarmerScreen(
            navigateToListScreen = navigateToListScreen,
            sharedViewModel = sharedViewModel,
            selectedFarmer = selectedFarmer
        )
    }
}