package com.android.bg_farmers.ui.screens.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.bg_farmers.ui.theme.LARGE_PADDING
import com.android.bg_farmers.ui.theme.MEDIUM_PADDING
import com.android.bg_farmers.R

@Composable
fun FarmerContent(
    name: String,
    onNameChange: (String) -> Unit,
    cropType: String,
    onCropTypeChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                onNameChange(it)
            },
            label = { Text(text = stringResource(id = R.string.name)) },
            textStyle = MaterialTheme.typography.body1,
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = cropType,
            onValueChange = {
                onCropTypeChange(it)
            },
            label = { Text(text = stringResource(id = R.string.crop_type)) },
            textStyle = MaterialTheme.typography.body1,
        )
    }
}

@Composable
@Preview
fun TaskContentPreview() {
    FarmerContent(
        name = "Sample",
        onNameChange = {},
        cropType = "Sample Description",
        onCropTypeChange = {},
    )
}