package io.moxd.multiband.ui.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import io.moxd.multiband.model.contactlist.Contact
import io.moxd.multiband.model.contactlist.ContactGroup
import io.moxd.multiband.model.contactlist.ContactRepo
import io.moxd.multiband.model.contactlist.getSeparatedByFirstLetter
import io.moxd.multiband.ui.theme.MyWearOSTheme
import io.moxd.multiband.ui.theme.WatchPreview

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contactGroupsSeparatedByLetter: List<ContactGroup>,
    scrollState: ScrollState = rememberScrollState(),
    enableTouchScroll: Boolean = true,
    onClickContact: (Contact) -> Unit,
    onContactHeightChange: (Int) -> Unit = {},
    onContactDividerHeightChange: (Int) -> Unit = {},
    onGroupSpacerHeightChange: (Int) -> Unit = {},
    onGroupTitleHeightChange: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .disableTouchScroll(!enableTouchScroll)
            .verticalScroll(scrollState)
    ) {
        for (contactGroup in contactGroupsSeparatedByLetter) {
            ContactGroup(
                contactGroup = contactGroup,
                onClickContact = onClickContact,
                onContactHeightChange = onContactHeightChange,
                onContactDividerHeightChange = onContactDividerHeightChange,
                onGroupTitleHeightChange = onGroupTitleHeightChange
            )
            Spacer(modifier = Modifier
                .size(10.dp)
                .onSizeChanged {
                    onGroupSpacerHeightChange(it.height)
                })
        }
    }
}

@WatchPreview
@Composable
fun ContactListPreview() {
    val contactsSeparatedByLetter = ContactRepo.contacts.getSeparatedByFirstLetter()
    MyWearOSTheme {
        ContactList(
            contactGroupsSeparatedByLetter = contactsSeparatedByLetter,
            onClickContact = {}
        )
    }
}