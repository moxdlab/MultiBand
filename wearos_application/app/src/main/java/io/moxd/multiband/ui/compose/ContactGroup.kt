package io.moxd.multiband.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import io.moxd.multiband.model.contactlist.Contact
import io.moxd.multiband.model.contactlist.ContactGroup
import io.moxd.multiband.model.contactlist.exampleContactGroup
import io.moxd.multiband.ui.theme.MyWearOSTheme
import io.moxd.multiband.ui.theme.WatchPreview
import io.moxd.multiband.utils.pxToDp

@Composable
fun ContactGroup(
    modifier: Modifier = Modifier,
    contactGroup: ContactGroup,
    onClickContact: (Contact) -> Unit = {},
    onContactHeightChange: (Int) -> Unit = {},
    onContactDividerHeightChange: (Int) -> Unit = {},
    onGroupTitleHeightChange: (Int) -> Unit = {}
) {
    val verticalGroupTitlePadding = 20
    val verticalDividerPadding = 10
    val verticalCardPadding = 20
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.onSizeChanged { onGroupTitleHeightChange(it.height) }
        ) {
            Text(
                text = contactGroup.index.toString(),
                style = MaterialTheme.typography.title3,
                modifier = Modifier
                    .padding(verticalGroupTitlePadding.pxToDp())
            )
        }
        Card(
            onClick = {},
            backgroundPainter = BrushPainter(
                brush = Brush.sweepGradient(
                    listOf(MaterialTheme.colors.onPrimary, MaterialTheme.colors.onPrimary)
                )
            ),
            contentPadding = PaddingValues(
                vertical = verticalCardPadding.pxToDp(),
                horizontal = 10.dp
            )
        ) {
            Column {
                contactGroup.contacts.forEachIndexed { index, contact ->
                    if (index != 0)
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(
                                    vertical = verticalDividerPadding.pxToDp(),
                                    horizontal = 10.dp
                                )
                                .onSizeChanged {
                                    onContactDividerHeightChange(it.height + verticalDividerPadding * 2)
                                },
                            thickness = 1.dp,
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.2f)
                        )
                    ContactRow(
                        modifier = Modifier.fillMaxWidth(),
                        contact = contact,
                        onClickContact = onClickContact,
                        onSizeChanged = onContactHeightChange
                    )
                }
            }
        }
    }
}

@WatchPreview
@Composable
private fun ContactGroupPreview() {
    ContactGroup(
        contactGroup = exampleContactGroup,
        onClickContact = {}
    )
}

@Composable
fun ContactRow(
    contact: Contact,
    onClickContact: (Contact) -> Unit,
    modifier: Modifier = Modifier,
    onSizeChanged: (Int) -> Unit = {}
) {
    val rowPaddingPx = 6
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(rowPaddingPx.pxToDp())
            .clickable(
                enabled = true,
                onClick = { onClickContact(contact) }
            )
            .onSizeChanged { onSizeChanged(it.height + rowPaddingPx * 2) }
    ) {
        LetterIcon(letter = contact.toString().first().toString())
        Text(
            "$contact",
            maxLines = 1,
            style = MaterialTheme.typography.caption1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .padding(start = 5.dp)
        )
    }
}

@WatchPreview
@Composable
private fun ContactRowPreview() {
    MyWearOSTheme {
        ContactRow(contact = Contact("Max", "Mustermann"), {})
    }
}

@Composable
fun LetterIcon(letter: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primaryVariant)
    ) {
        Text(text = letter, style = MaterialTheme.typography.title1)
    }
}

@WatchPreview
@Composable
private fun LetterIconPreview() {
    val exampleLetter = "A"
    LetterIcon(exampleLetter)
}