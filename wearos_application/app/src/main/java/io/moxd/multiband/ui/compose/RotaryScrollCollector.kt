package io.moxd.multiband.ui.compose

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.RotaryScrollEvent
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun RotaryScrollCollector(onCollectRotaryScrollEvent: (RotaryScrollEvent) -> Unit) {
    val listState = rememberLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .onRotaryScrollEvent {
                    onCollectRotaryScrollEvent(it)
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            state = listState
        ) {}
    }
}