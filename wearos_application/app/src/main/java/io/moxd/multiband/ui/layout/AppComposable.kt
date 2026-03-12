package io.moxd.multiband.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import io.moxd.multiband.model.screen.UIElementSizes.screenHeight

@Composable
fun AppComposable() {
    val swipeDismissibleNavController = rememberSwipeDismissableNavController()
    with(LocalDensity.current) {
        screenHeight = LocalConfiguration.current.screenHeightDp.dp.roundToPx()
    }
    SwipeDismissableNavHost(
        navController = swipeDismissibleNavController,
        startDestination = Screen.Playground.route
    ) {
        composable(route = Screen.Playground.route) {
            ContactlistScreen()
        }
    }
}

sealed class Screen(
    val route: String
) {
    object Playground : Screen("playground")
}