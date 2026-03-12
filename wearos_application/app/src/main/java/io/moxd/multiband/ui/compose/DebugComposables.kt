package io.moxd.multiband.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Text

@Composable
fun DebugButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier.alpha(1f),
        onClick = onClick
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun DebugButtonPreview() {
    DebugButton(text = "1")
}