package com.android.bg_farmers.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bg_farmers.R
import com.android.bg_farmers.ui.theme.LIST_EMPTY_SAD_ICON_SIZE

@Composable
fun ListEmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(LIST_EMPTY_SAD_ICON_SIZE)
                .alpha(0.5f),
            painter = painterResource(id = R.drawable.ic_notepad),
            contentDescription = stringResource(id = R.string.list_empty_icon),
        )
        Text(
            modifier = Modifier
                .alpha(0.5f),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.list_empty_content),
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
@Preview
fun ListEmptyContentPreview() {
    ListEmptyContent()
}