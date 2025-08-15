package com.example.internationalmodule1.screens

import android.icu.util.TimeUnit
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internationalmodule1.LocalDataModel
import com.example.internationalmodule1.LocalNavController
import com.example.internationalmodule1.models.MyBooking
import com.example.internationalmodule1.models.Screen
import com.example.internationalmodule1.models.convertDate
import com.example.internationalmodule1.models.paymentMethod
import java.sql.Time
import java.text.Normalizer.Form
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun ConfirmScreen() {
    val nav = LocalNavController.current
    val data = LocalDataModel.current
    val detail = data.hotelDetails[data.currentDetailsId]!!
    val room = detail.rooms.find { it.roomId == data.bookingRoomId }!!

    Column(modifier = Modifier.statusBarsPadding()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.pop() }) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
            Text("Booking Confirm")
        }
        HorizontalDivider()
        Text(
            "You are going to reserve:",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.BottomCenter

        ) {
            Card(
                elevation = CardDefaults.elevatedCardElevation(15.dp),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Image(
                    bitmap = data.allHotel.find { it.hotelId == data.currentDetailsId }!!.hotelCoverImage,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.4f
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    detail.hotelName,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Card(
                    modifier = Modifier.padding(15.dp),
                    elevation = CardDefaults.elevatedCardElevation(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(room.roomType, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                            Text(
                                "€${room.roomPriceForOneNight}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }

        data class BookingConfirmData(
            val id: Int = 0,
            val firstName: String = "",
            val lastName: String = "",
            val checkIn: String = "",
            val checkOut: String = "",
            val adultsCount: String = "1",
            val childrenCount: String = "0",
            val roomsCount: Int = 0,
            val isBusiness: Boolean = false,
            val paymentMethod: Int = 0,
            val price: Int = 0
        )

        var formData by remember { mutableStateOf(BookingConfirmData()) }
        val gridTextFields = listOf<Triple<String, String, (String) -> Unit>>(Triple(
            "First Name", formData.firstName
        ) { formData = formData.copy(firstName = it) }, Triple(
            "Last Name",
            formData.lastName,
        ) { formData = formData.copy(lastName = it) }, Triple(
            "Check-in date",
            formData.checkIn,
        ) {
            formData = formData.copy(checkIn = it)
        }, Triple(
            "Check-out date", formData.checkOut
        ) { formData = formData.copy(checkOut = it) })

        LazyColumn(contentPadding = PaddingValues(10.dp), modifier = Modifier.weight(3f)) {
            item {
                Text("Form", style = boldTitle)
            }
            item {
                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    userScrollEnabled = false,
                    modifier = Modifier.height(56.dp * 2 + 10.dp * 3)
                ) {
                    items(gridTextFields) {
                        FormTextField(
                            hintText = it.first,
                            value = it.second,
                            onValueChange = it.third,
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .padding(bottom = 5.dp)
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
                Text("Double Room with Matterhorn View", style = boldTitle)
            }
            item {
                Row(
                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
                ) {
                    FormTextField(
                        hintText = "Adults",
                        value = formData.adultsCount,
                        modifier = Modifier.weight(1f),
                        type = KeyboardType.Number
                    ) { formData = formData.copy(adultsCount = it) }
                    Spacer(Modifier.width(10.dp))
                    FormTextField(
                        hintText = "Children",
                        value = formData.childrenCount,
                        modifier = Modifier.weight(1f),
                        type = KeyboardType.Number
                    ) { formData = formData.copy(childrenCount = it) }
                    Spacer(Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        LaunchedEffect(formData) {
                            val totalGuest = (formData.adultsCount.toIntOrNull()
                                ?: 0) + (formData.childrenCount.toIntOrNull() ?: 0)
                            val totalRoom =
                                totalGuest / room.roomTotalNumberOfGuests + if (totalGuest % room.roomTotalNumberOfGuests != 0) 1 else 0
                            formData = formData.copy(roomsCount = totalRoom)
                        }

                        Text("Room")
                        Text(
                            formData.roomsCount.toString(), fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            item {
                Text("Travel for business?")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FormRadio(selected = !formData.isBusiness, label = "For sightseeing") {
                        formData = formData.copy(isBusiness = false)
                    }
                    FormRadio(
                        selected = formData.isBusiness,
                        label = "+ € 150\nFor business with a meeting room"
                    ) {
                        formData = formData.copy(isBusiness = true)
                    }
                }
            }
            item {
                Text("Which way to pay?")
                Row {
                    paymentMethod.forEachIndexed { index, item ->
                        FormRadio(selected = formData.paymentMethod == index, label = item) {
                            formData = formData.copy(paymentMethod = index)
                        }
                    }
                }
            }
        }

        LaunchedEffect(formData) {
            val day = java.util.concurrent.TimeUnit.DAYS.toMillis(1)
            val checkIn = try {
                convertDate(formData.checkIn)
            } catch (e: Exception) {
                System.currentTimeMillis()
            }
            val checkOut = try {
                convertDate(formData.checkOut)
            } catch (e: Exception) {
                System.currentTimeMillis() + day
            }
            formData =
                formData.copy(price = formData.roomsCount * room.roomPriceForOneNight * ((checkOut - checkIn) / day).toInt() + if (formData.isBusiness) 150 else 0)
        }

        var showErrorDialog by remember { mutableStateOf(false) }
        var showConfirmDialog by remember { mutableStateOf(false) }
        var errorHint by remember { mutableStateOf("") }
        var willAdded by remember { mutableStateOf<MyBooking?>(null) }

        if (showErrorDialog) {
            AlertDialog(onDismissRequest = { showErrorDialog = false },
                title = { Text("Wrong format") },
                text = { Text(errorHint) },
                confirmButton = { Button(onClick = { showErrorDialog = false }) { Text("Ok") } })
        }

        if (showConfirmDialog) {
            AlertDialog(onDismissRequest = { showConfirmDialog = false },
                title = { Text("Are you sure") },
                text = { Text("Are you going to book this room?") },
                confirmButton = {
                    Button(onClick = {
                        data.myBookings.add(willAdded!!)
                        nav.navTo(Screen.MyBooking)
                    }) { Text("Yes") }
                },
                dismissButton = {
                    FilledTonalButton(onClick = { showConfirmDialog = false }) {
                        Text(
                            "No"
                        )
                    }
                })
        }

        Column(modifier = Modifier.weight(1f)) {
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 5.dp), horizontalArrangement = Arrangement.End
            ) {
                Text("Total:  ", fontWeight = FontWeight.Bold)
                Text(
                    "€ ${formData.price}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
            FilledTonalButton(
                onClick = {
                    try {
                        val firstName = formData.firstName.trim()
                        val lastName = formData.lastName.trim()
                        val adults = formData.adultsCount.toInt()
                        val children = formData.childrenCount.toInt()
                        val checkIn = convertDate(formData.checkIn)
                        val checkOut = convertDate(formData.checkOut)

                        when {
                            firstName.length !in (1..15) || lastName.length !in (1..15) -> throw Exception(
                                "First name or Last name length must in 1~15"
                            )

                            !firstName.all { it in 'a'..'z' || it in 'A'..'Z' } || !lastName.all { it in 'a'..'z' || it in 'A'..'Z' } -> throw Exception(
                                "First name or Last name must only contain letters"
                            )

                            adults == 0 && children != 0 -> throw Exception("Occupier can't be children only")

                            adults !in (1..5) -> throw Exception("Number of adults must in 1~5")

                            children !in listOf(
                                0,
                                adults * 1,
                                adults * 2
                            ) -> throw Exception("Number of children must be 0~2 times of adults")

                            checkIn > checkOut -> throw Exception("CheckIn date must be earlier than CheckOut date")
                        }
                        willAdded = MyBooking(
                            id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
                            hotelName = detail.hotelName,
                            firstName = formData.firstName,
                            lastName = formData.lastName,
                            checkIn = checkIn,
                            checkOut = checkOut,
                            adultsCount = formData.adultsCount.toInt(),
                            childrenCount = formData.childrenCount.toInt(),
                            isBusiness = formData.isBusiness,
                            paymentMethod = formData.paymentMethod,
                            roomsCount = formData.roomsCount,
                            price = formData.price
                        )
                        showConfirmDialog = true
                    } catch (e: Exception) {
                        errorHint = e.message ?: "something goose wrong with the form input format"
                        showErrorDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Book Now")
            }
        }
    }
}

@Composable
fun FormRadio(selected: Boolean, label: String, onClick: () -> Unit) {
    Row(modifier = Modifier
        .clip(CircleShape)
        .clickable { onClick() }
        .padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Text(label, fontSize = 12.sp, lineHeight = 12.sp)
    }
}

@Composable
fun FormTextField(
    value: String,
    hintText: String,
    modifier: Modifier = Modifier,
    type: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value,
        onValueChange,
        singleLine = true,
        label = { Text(hintText) },
        modifier = Modifier.then(modifier),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = type)
    )
}