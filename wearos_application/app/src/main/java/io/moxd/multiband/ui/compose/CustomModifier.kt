package io.moxd.multiband.ui.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll


fun Modifier.disableTouchScroll(disable: Boolean = true): Modifier =
    if (disable) {
        val verticalScrollConsumer = object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
                available.copy(x = 0F)
        }
        this then Modifier.nestedScroll(verticalScrollConsumer)
    } else this

