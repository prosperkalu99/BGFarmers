package com.android.bg_farmers.ui.screens.farmer

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.android.bg_farmers.data.models.Farmer
import com.android.bg_farmers.ui.viewmodels.SharedViewModel
import com.android.bg_farmers.util.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FarmerScreen(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedFarmer: Farmer?
) {

    val name: String by sharedViewModel.name
    val cropType: String by sharedViewModel.cropType

    val context = LocalContext.current

    Scaffold(
        topBar = {
            FarmerAppBar(
                navigateToListScreen = {
                    if (it == Action.NO_ACTION) {
                        navigateToListScreen(it)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(it)
                        } else {
                            Toast.makeText(context, "Fields Empty", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                selectedTask = selectedFarmer)
        },
        content = {
            FarmerContent(
                name = name,
                onNameChange = {
                    sharedViewModel.name.value = it
                },
                cropType = cropType,
                onCropTypeChange = {
                    sharedViewModel.cropType.value = it
                },
            )
        }
    )
}