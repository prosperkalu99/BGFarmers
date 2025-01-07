package com.android.bg_farmers.ui.screens.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.android.bg_farmers.R
import com.android.bg_farmers.components.DisplayAlertDialog
import com.android.bg_farmers.data.models.Farmer
import com.android.bg_farmers.ui.theme.MEDIUM_PADDING
import com.android.bg_farmers.util.Action

@Composable
fun FarmerAppBar(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: Farmer?
) {
    if (selectedTask == null) {
        NewTaskAppBar(
            navigateToListScreen = navigateToListScreen
        )
    } else {
        ExistingTaskAppBar(
            farmer = selectedTask,
            navigateToListScreen = navigateToListScreen
        )
    }
}

@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.new_farmer),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            SaveAction(onSaveClicked = navigateToListScreen)
        },
    )
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(MEDIUM_PADDING),
        onClick = { onBackClicked(Action.NO_ACTION) }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.arrow_back),
        )
    }
}

@Composable
fun SaveAction(
    onSaveClicked: (Action) -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(MEDIUM_PADDING),
        onClick = { onSaveClicked(Action.ADD) }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.farmer_save),
        )
    }
}

@Composable
fun ExistingTaskAppBar(
    farmer: Farmer,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = farmer.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            ExistingTaskAppBarActions(
                farmer = farmer,
                navigateToListScreen = navigateToListScreen
            )
        },
    )
}

@Composable
fun ExistingTaskAppBarActions(
    farmer: Farmer,
    navigateToListScreen: (Action) -> Unit
) {

    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_task_title, farmer.name),
        message = stringResource(id = R.string.delete_task_message, farmer.name),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETE) }
    )
    DeleteAction(onDeleteClicked = {
        openDialog = true
    })

    UpdateAction(onUpdateClicked = navigateToListScreen)
}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(MEDIUM_PADDING),
        onClick = { onCloseClicked(Action.NO_ACTION) }
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_farmer),
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(MEDIUM_PADDING),
        onClick = { onDeleteClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_farmer),
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(MEDIUM_PADDING),
        onClick = { onUpdateClicked(Action.UPDATE) }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_farmer),
        )
    }
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar {}
}

@Composable
@Preview
fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        farmer = Farmer(0, "Sample Task", "Sample Description"),
        navigateToListScreen = {}
    )
}