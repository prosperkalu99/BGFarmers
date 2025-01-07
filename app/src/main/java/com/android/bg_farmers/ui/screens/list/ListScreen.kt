package com.android.bg_farmers.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.android.bg_farmers.R
import com.android.bg_farmers.ui.viewmodels.SharedViewModel
import com.android.bg_farmers.util.Action
import com.android.bg_farmers.util.SearchAppBarState
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToFarmerScreen: (farmerId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllFarmers()
    }

    val action by sharedViewModel.action
    val allFarmers by sharedViewModel.allFarmers.collectAsState()
    val searchedFarmers by sharedViewModel.searchedFarmers.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()
    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseAction = {
            sharedViewModel.handleDatabaseActions(action)
        },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        farmerName = sharedViewModel.name.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(sharedViewModel, searchAppBarState, searchTextState)
        },
        content = {
            ListContent(
                allFarmers = allFarmers,
                searchedFarmers = searchedFarmers,
                searchAppBarState = searchAppBarState,
                navigateToFarmerScreen = navigateToFarmerScreen,
                onSwipeToDelete = { action, todoTask ->
                    sharedViewModel.updateFarmerFields(todoTask)
                    sharedViewModel.action.value = action
                }
            )
        },
        floatingActionButton = {
            AddFarmer(onAddFarmerClicked = navigateToFarmerScreen)
        }
    )
}

@Composable
fun AddFarmer(
    onAddFarmerClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onAddFarmerClicked(-1)
        },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.register_farmer),
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseAction: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    farmerName: String,
    action: Action
) {
    Log.d("mycheck", action.name)
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setSnackBarMessage(action, farmerName),
                    actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setSnackBarMessage(action: Action, farmerName: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Farmers Deleted."
        else -> "${action.name}: $farmerName"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == Action.DELETE.name) "UNDO" else "OK"
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}