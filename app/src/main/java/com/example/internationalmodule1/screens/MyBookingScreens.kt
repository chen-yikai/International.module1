package com.example.internationalmodule1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internationalmodule1.LocalDataModel
import com.example.internationalmodule1.LocalNavController
import com.example.internationalmodule1.models.Screen
import com.example.internationalmodule1.models.paymentMethod
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MyBookingsScreen() {
    val nav = LocalNavController.current
    val data = LocalDataModel.current

    Column(modifier = Modifier.statusBarsPadding()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.navTo(Screen.Home) }) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
            Text("My bookings")
        }
        HorizontalDivider()
        LazyColumn {
            itemsIndexed(data.myBookings) { index, item ->
                Card(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            (index + 1).toString(),
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                        Column(modifier = Modifier.padding(start = 5.dp)) {
                            Text(
                                "${item.firstName} ${item.lastName}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                            Text(item.hotelName, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                            val smallText = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Black)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val pattern = "EEE, MMM dd, yyyy"
                                Text(
                                    SimpleDateFormat(pattern, Locale.US).format(item.checkIn),
                                    style = smallText
                                )
                                Icon(
                                    Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    SimpleDateFormat(pattern, Locale.US).format(item.checkOut),
                                    style = smallText
                                )
                            }
                            Text(
                                "${item.adultsCount} Adults, ${item.childrenCount} Children, ${item.roomsCount} Room",
                                style = smallText
                            )
                            val smallLabel = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp, color = Color.Gray
                            )
                            Spacer(Modifier.height(5.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        if (item.isBusiness) "For business with a meeting room" else "For sightseeing",
                                        style = smallLabel
                                    )
                                    Text(
                                        "Pay with ${paymentMethod[item.paymentMethod]}",
                                        style = smallLabel
                                    )
                                }
                                Text(
                                    "€ ${item.price}",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}