package com.example.internationalmodule1.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internationalmodule1.LocalDataModel
import com.example.internationalmodule1.LocalNavController
import com.example.internationalmodule1.models.HotelDetails
import com.example.internationalmodule1.models.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailsScreen() {
    val nav = LocalNavController.current
    val data = LocalDataModel.current
    var selectedIndex by remember { mutableIntStateOf(0) }
    val selectItems = listOf("Guest reviews", "Room selection")
    val detail = data.hotelDetails[data.currentDetailsId]!!

    Column(modifier = Modifier.statusBarsPadding()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.pop() }) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
            Text("Booking")
        }
        HorizontalDivider()
        PrimaryTabRow(selectedTabIndex = selectedIndex) {
            selectItems.forEachIndexed { index, item ->
                Tab(selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    text = { Text(item) })
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = data.allHotel.find { it.hotelId == data.currentDetailsId }!!.hotelCoverImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop, alpha = 0.5f
            )
            Text(
                detail.hotelName,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        when (selectedIndex) {
            0 -> GuestReviews(detail)
            1 -> AllRoom(detail)
        }
    }
}

val boldTitle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)

@Composable
fun GuestReviews(detail: HotelDetails) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 5.dp)
            ) {
                Text("Ratings", style = boldTitle)
            }
        }
        items(detail.guestReviews.ratingsCategories.toList()) {
            Column(modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)) {
                Row {
                    Text(it.first)
                    Spacer(Modifier.weight(1f))
                    Text(it.second.toString())
                }
                LinearProgressIndicator(
                    progress = { it.second / 10f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    strokeCap = StrokeCap.Round,
                )
            }
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text("Reviews", style = boldTitle, modifier = Modifier.padding(vertical = 10.dp))
            }
        }
        item {
            LazyRow(contentPadding = PaddingValues(start = 10.dp)) {
                items(detail.guestReviews.reviewsObjects, key = { it.username }) {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .widthIn(max = 200.dp)
                            .heightIn(min = 250.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        it.username.first().toString(),
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp
                                    )
                                }
                                Spacer(Modifier.width(5.dp))
                                Column {
                                    Text(it.username, fontWeight = FontWeight.Bold)
                                    Text(
                                        it.country,
                                        color = Color.Gray,
                                        fontSize = 10.sp, lineHeight = 10.sp
                                    )
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(it.reviewText, fontSize = 14.sp, lineHeight = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun AllRoom(detail: HotelDetails) {
    val data = LocalDataModel.current
    val nav = LocalNavController.current

    LazyColumn(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)) {
        stickyHeader {
            Text(
                "Rooms",
                style = boldTitle,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            )
        }
        items(detail.rooms) { room ->
            Card(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(), onClick = {
                    data.bookingRoomId = room.roomId
                    nav.navTo(Screen.BookingConfirm)
                }
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(room.roomType, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Bed: ", style = MaterialTheme.typography.labelMedium)
                        Text(room.roomBedType, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Total number of guests: ",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            room.roomTotalNumberOfGuests.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        FlowRow(modifier = Modifier.widthIn(max = 300.dp)) {
                            room.roomFeatures.forEach { feature ->
                                Text(
                                    feature,
                                    fontSize = 10.sp,
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .padding(bottom = 5.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(horizontal = 5.dp, vertical = 2.dp),
                                    lineHeight = 10.sp
                                )
                            }
                        }
                        Text("€${room.roomPriceForOneNight}", fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}