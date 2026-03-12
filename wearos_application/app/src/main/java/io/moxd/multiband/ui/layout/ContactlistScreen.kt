package io.moxd.multiband.ui.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.moxd.multiband.model.contactlist.Contact
import io.moxd.multiband.model.contactlist.ContactGroup
import io.moxd.multiband.model.contactlist.ContactRepo
import io.moxd.multiband.model.contactlist.getSeparatedByFirstLetter
import io.moxd.multiband.model.persistence.studyContactGroups
import io.moxd.multiband.model.screen.UIElementSizes
import io.moxd.multiband.ui.compose.ContactList
import io.moxd.multiband.ui.compose.DebugButton
import io.moxd.multiband.ui.theme.WatchPreview
import io.moxd.multiband.viewmodel.ContactlistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ContactlistScreen(
    playgroundViewModel: ContactlistViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val isLoading by playgroundViewModel.isLoading.observeAsState(true)
    val numberOfFingers by playgroundViewModel.debugData.observeAsState(listOf(1))
    val scope = rememberCoroutineScope()

    if (isLoading) {
        LoadingScreen()
    } else {
        val modifier = Modifier
        ContactlistScreen(
            modifier = modifier,
            sortedContactGroups = studyContactGroups,
            scrollState = scrollState,
            debugEnabled = playgroundViewModel.debugEnabled,
            debugText = "${numberOfFingers.last()}",
            onClickContact = {},
            onContactHeightChange = { UIElementSizes.contactHeight = it },
            onContactDividerHeightChange = { UIElementSizes.contactDividerHeight = it },
            onGroupSpacerHeightChange = { UIElementSizes.groupSpacerHeight = it },
            onGroupTitleHeightChange = { UIElementSizes.groupTitleHeight = it },
            onClickDebugButton = { playgroundViewModel.changeDebugData() }
        )

        val update = rememberUpdatedState(newValue = true)
        LaunchedEffect(true) {
            scope.launch(Dispatchers.Main) {
                playgroundViewModel.incomingDelta.collect {
                    scrollState.scrollBy(it)
                }
            }
        }
    }
}

@Composable
fun ContactlistScreen(
    modifier: Modifier = Modifier,
    sortedContactGroups: List<ContactGroup>,
    scrollState: ScrollState,
    debugEnabled: Boolean,
    debugText: String = "",
    onClickContact: (Contact) -> Unit = {},
    onContactHeightChange: (Int) -> Unit = {},
    onContactDividerHeightChange: (Int) -> Unit = {},
    onGroupSpacerHeightChange: (Int) -> Unit = {},
    onGroupTitleHeightChange: (Int) -> Unit = {},
    onClickDebugButton: () -> Unit = {}
) {
    Box(contentAlignment = Alignment.CenterEnd) {
        ContactList(
            modifier = modifier,
            enableTouchScroll = false,
            contactGroupsSeparatedByLetter = sortedContactGroups,
            scrollState = scrollState,
            onClickContact = {
                onClickContact(it)
            },
            onContactHeightChange = onContactHeightChange,
            onContactDividerHeightChange = onContactDividerHeightChange,
            onGroupSpacerHeightChange = onGroupSpacerHeightChange,
            onGroupTitleHeightChange = onGroupTitleHeightChange
        )

        if (debugEnabled)
            DebugButton(
                onClick = onClickDebugButton,
                text = debugText
            )
    }
}

@WatchPreview
@Composable
fun PlaygroundPreview() {
    ContactlistScreen(
        debugEnabled = true,
        sortedContactGroups = ContactRepo.contacts.getSeparatedByFirstLetter(),
        scrollState = rememberScrollState()
    )
}