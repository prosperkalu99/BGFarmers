package com.android.bg_farmers.navigation

import androidx.navigation.NavHostController
import com.android.bg_farmers.util.Action
import com.android.bg_farmers.util.Constants.LIST_SCREEN
import com.android.bg_farmers.util.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {

    val splash: () -> Unit = { ->
        navController.navigate(route = "list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }

    val list: (Int) -> Unit = { farmerId ->
        navController.navigate(route = "farmer/$farmerId")
    }

    val farmer: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

}