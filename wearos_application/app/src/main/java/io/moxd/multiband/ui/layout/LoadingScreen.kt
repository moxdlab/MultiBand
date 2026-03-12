package io.moxd.multiband.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import io.moxd.multiband.ui.theme.WatchPreview

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

@WatchPreview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}