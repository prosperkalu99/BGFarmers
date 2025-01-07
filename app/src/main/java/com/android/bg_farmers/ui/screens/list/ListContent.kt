package com.android.bg_farmers.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.android.bg_farmers.R
import com.android.bg_farmers.data.models.Farmer
import com.android.bg_farmers.ui.theme.LARGEST_PADDING
import com.android.bg_farmers.ui.theme.LARGE_PADDING
import com.android.bg_farmers.ui.theme.FARMER_ITEM_ELEVATION
import com.android.bg_farmers.util.Action
import com.android.bg_farmers.util.RequestState
import com.android.bg_farmers.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    allFarmers: RequestState<List<Farmer>>,
    searchedFarmers: RequestState<List<Farmer>>,
    searchAppBarState: SearchAppBarState,
    navigateToFarmerScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, Farmer) -> Unit,
) {
    when (searchAppBarState) {
        SearchAppBarState.TRIGGERED -> {
            if (searchedFarmers is RequestState.Success) {
                HandleListContent(
                    farmers = searchedFarmers.data,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToFarmerScreen
                )
            }
        }
        else -> {
            if (allFarmers is RequestState.Success) {
                HandleListContent(
                    farmers = allFarmers.data,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToFarmerScreen
                )
            }
        }
    }
}

@Composable
fun HandleListContent(
    farmers: List<Farmer>,
    onSwipeToDelete: (Action, Farmer) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (farmers.isEmpty()) ListEmptyContent()
    else {
        DisplayFarmers(
            farmers = farmers,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayFarmers(
    farmers: List<Farmer>,
    onSwipeToDelete: (Action, Farmer) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = farmers,
            key = { task ->
                task.id
            }
        ) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, task)
                }
            }
            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }
            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    tween(durationMillis = 300)
                ),
                exit = shrinkVertically(
                    tween(durationMillis = 300)
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {
                        FractionalThreshold(0.2f)
                    },
                    background = {
                        RedBackground(degrees = degrees)
                    },
                    dismissContent = {
                        FarmerItem(farmer = task, navigateToFarmerScreen = navigateToTaskScreen)
                    }
                )
            }
        }
    }
}

@Composable
fun RedBackground(
    degrees: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@Composable
fun FarmerItem(
    farmer: Farmer,
    navigateToFarmerScreen: (farmerId: Int) -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToFarmerScreen(farmer.id)
            },
        shape = RectangleShape,
        elevation = FARMER_ITEM_ELEVATION,
    ){
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = farmer.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = farmer.cropType,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun FarmerItemPreview() {
    FarmerItem(
        farmer = Farmer(0, "Sample Title", "This is the sample body description of the todo. This is the sample body description of the todo. This is the sample body description of the todo.This is the sample body description of the todo"),
        navigateToFarmerScreen = {}
    )
}