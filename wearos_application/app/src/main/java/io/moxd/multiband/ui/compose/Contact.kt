package io.moxd.multiband.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import io.moxd.multiband.R
import io.moxd.multiband.model.contactlist.Contact
import io.moxd.multiband.ui.theme.WatchPreview

@Composable
fun Contact(contact: Contact, onPressCallButton: (Contact) -> Unit = {}){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 5.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ContactIcon(letter = contact.toString().first().toString())
            NameText(contact)
            if (contact.number != null) {
                NumberCard(contact.number)
                CallAndMessageButton(
                    onPressCallButton = { onPressCallButton(contact) }
                )
            }
        }
    }
}

@WatchPreview
@Composable
fun ContactPreview(){
    Contact(contact = Contact("Max", "Mustermann", "+49157123456789"))
}

@Composable
fun NameText(contact: Contact){
    Text(
        text = "$contact",
        style = MaterialTheme.typography.title2,
        maxLines = 1,
        modifier = Modifier.padding(6.dp)
    )
}

@WatchPreview
@Composable
fun NameTextPreview(){
    NameText(contact = Contact("Max", "Mustermann", "+49157123456789"))
}

@Composable
fun CallAndMessageButton(
    onPressCallButton: () -> Unit = {},
    onPressMessageButton: () -> Unit = {}
){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(5.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color.Green)
        ) {
            IconButton(onClick = { onPressCallButton() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_local_phone_24),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            IconButton(onClick = { onPressMessageButton() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_message_24),
                    contentDescription = null
                )
            }
        }
    }
}

@WatchPreview
@Composable
fun CallAndMessageButtonPreview(){
    CallAndMessageButton()
}

@Composable
fun NumberCard(number: String){
    Card(
        onClick = {},
        backgroundPainter = BrushPainter(
            brush = Brush.sweepGradient(
                listOf(MaterialTheme.colors.onPrimary, MaterialTheme.colors.onPrimary)
            )
        )
    ) {
        Text(text = "Phone", style = MaterialTheme.typography.title3)
        Text(text = number, style = MaterialTheme.typography.caption1)
    }
}

@WatchPreview
@Composable
fun NumberCardPreview(){
    NumberCard(number = "+4915712342424")
}

@Composable
fun ContactIcon(letter: String, modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primaryVariant)
    ){
        Text(text = letter, style = MaterialTheme.typography.title1)
    }
}

@WatchPreview
@Composable
private fun ContactIconPreview(){
    val exampleLetter = "A"
    ContactIcon(exampleLetter)
}