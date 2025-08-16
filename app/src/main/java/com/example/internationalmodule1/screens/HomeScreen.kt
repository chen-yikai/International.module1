package com.example.internationalmodule1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internationalmodule1.LocalDataModel
import com.example.internationalmodule1.LocalNavController
import com.example.internationalmodule1.R
import com.example.internationalmodule1.TestTag
import com.example.internationalmodule1.models.Screen
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    val nav = LocalNavController.current
    val data = LocalDataModel.current
    var searchText by remember { mutableStateOf("") }

    Column(modifier = Modifier.statusBarsPadding()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("The Alps' Hotel", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Image(
                    painter = painterResource(R.drawable.france_national_flag),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { nav.navTo(Screen.MyBooking) }) {
                Icon(
                    painter = painterResource(R.drawable.account), contentDescription = null
                )
            }
        }
        HorizontalDivider()
        OutlinedTextField(
            searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search a hotel name") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .testTag(TestTag.Home.searchBar),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {})
        )
        LazyColumn(modifier = Modifier.testTag(TestTag.Home.hotelList)) {
            items(data.allHotel) {
                if (it.hotelName.lowercase().contains(searchText.lowercase()))
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                bitmap = it.hotelCoverImage,
                                contentDescription = null,
                                Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                            ) {
                                Text(it.hotelName, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                it.hotelRating.toString(),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
                                                    .border(
                                                        1.dp, Color.Gray, RectangleShape
                                                    )
                                                    .padding(horizontal = 5.dp)
                                            )
                                            Spacer(Modifier.width(5.dp))
                                            repeat(it.hotelRating.roundToInt() / 2) {
                                                Image(
                                                    painter = painterResource(R.drawable.star),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(15.dp)
                                                )
                                            }
                                        }
                                        Spacer(Modifier.height(5.dp))
                                        Text(
                                            "${it.hotelToSkiDistance} km from Alps' ski lift",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            if (it.hotelId in data.hasHotelDetails) {
                                                nav.navTo(Screen.BookingDetails)
                                                data.currentDetailsId = it.hotelId
                                            }
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.testTag(it.hotelName)
                                    ) {
                                        Text("Book It")
                                    }
                                }
                            }
                        }
                    }
            }
            item {
                Spacer(
                    Modifier
                        .navigationBarsPadding()
                        .testTag(TestTag.Home.hotelListBottom)
                )
            }
        }
    }
}