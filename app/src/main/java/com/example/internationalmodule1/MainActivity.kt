package com.example.internationalmodule1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.internationalmodule1.models.DataModel
import com.example.internationalmodule1.models.MyBooking
import com.example.internationalmodule1.models.NavController
import com.example.internationalmodule1.models.Screen
import com.example.internationalmodule1.screens.ConfirmScreen
import com.example.internationalmodule1.screens.HomeScreen
import com.example.internationalmodule1.screens.HotelDetailsScreen
import com.example.internationalmodule1.screens.MyBookingsScreen
import com.example.internationalmodule1.ui.theme.Internationalmodule1Theme

val LocalNavController = compositionLocalOf<NavController> { error("") }
val LocalDataModel = compositionLocalOf<DataModel> { error("") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Internationalmodule1Theme {
                val nav = ViewModelProvider(this)[NavController::class.java]
                val data = ViewModelProvider(this)[DataModel::class.java]

                CompositionLocalProvider(
                    LocalNavController provides nav,
                    LocalDataModel provides data
                ) {
                    BackHandler {
                        if (nav.navStack.size > 1) {
                            nav.pop()
                        } else {
                            finish()
                        }
                    }
                    Surface {
                        when (nav.currentNav) {
                            Screen.Home -> HomeScreen()
                            Screen.BookingDetails -> HotelDetailsScreen()
                            Screen.BookingConfirm -> ConfirmScreen()
                            Screen.MyBooking -> MyBookingsScreen()
                            else -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) { Text("Can't found ${nav.currentNav.name}") }
                            }
                        }
                    }
                }
            }
        }
    }
}