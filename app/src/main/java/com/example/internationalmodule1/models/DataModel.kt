package com.example.internationalmodule1.models

import android.app.Application
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.AndroidViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertDate(text: String): Long {
    val formats = listOf(
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("MM-dd-yyyy"),
        DateTimeFormatter.ofPattern("MMM dd yyyy")
    )

    formats.forEach {
        try {
            val date = LocalDate.parse(text.trim(), it)
            return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (_: Exception) {
        }
    }
    throw Exception("Not a valid date format")
}

class DataModel(private val context: Application) : AndroidViewModel(context) {
    var allHotel = mutableStateListOf<Hotel>()
    val hasHotelDetails = listOf(1000, 1008)
    var hotelDetails = mutableMapOf<Int, HotelDetails>()
    var currentDetailsId by mutableIntStateOf(0)
    var bookingRoomId by mutableIntStateOf(0)
    var myBookings = mutableStateListOf<MyBooking>()

    init {
        initAllHotel()
        initHotelDetail()
    }

    private fun initHotelDetail() {
        hasHotelDetails.forEach { hotelId ->
            context.assets.open("hotels_details.$hotelId.json").bufferedReader().readText()
                .let { text ->
                    val detailsObject = JSONObject(text)
                    val guestReviews = detailsObject.getJSONObject("guest_reviews")
                    val ratingsCats = guestReviews.getJSONArray("ratings_categories").let { cats ->
                        val ratingCats = mutableMapOf<String, Float>()
                        repeat(cats.length()) { index ->
                            val cat = cats.getJSONObject(index)
                            val key = cat.keys().next()
                            val value = cat.getDouble(key).toFloat()
                            ratingCats[key] = value
                        }
                        ratingCats
                    }
                    val reviewsObjects = guestReviews.getJSONArray("reviews_objects").let { obj ->
                        List(obj.length()) { index ->
                            val item = obj.getJSONObject(index)
                            ReviewsObject(
                                username = item.getString("username"),
                                country = item.getString("country"),
                                reviewText = item.getString("review_text")
                            )
                        }
                    }
                    val roomArray = detailsObject.getJSONArray("rooms")
                    val rooms = List(roomArray.length()) { index ->
                        val item = roomArray.getJSONObject(index)
                        Room(
                            roomId = item.getInt("room_id"),
                            roomType = item.getString("room_type"),
                            roomBedType = item.getString("room_bed_type"),
                            roomTotalNumberOfGuests = item.getInt("room_total_number_of_guests"),
                            roomFeatures = item.getJSONArray("room_features").let { feature ->
                                List(feature.length()) { featureIndex ->
                                    feature.getString(featureIndex)
                                }
                            },
                            roomPriceForOneNight = item.getInt("room_price_for_one_night")
                        )
                    }
                    hotelDetails[hotelId] = HotelDetails(
                        hotelId = detailsObject.getInt("hotel_id"),
                        hotelName = detailsObject.getString("hotel_name"),
                        guestReviews = GuestReviews(
                            ratingsCategories = ratingsCats,
                            reviewsObjects = reviewsObjects
                        ),
                        rooms = rooms
                    )
                }
        }
    }

    private fun initAllHotel() {
        context.assets.open("hotels.json").bufferedReader().readText().let {
            val array = JSONArray(it)
            allHotel.clear()
            allHotel.addAll(List(array.length()) { index ->
                val item = array.getJSONObject(index)
                val coverPath = item.getString("hotel_cover_image")
                val bitmap = context.assets.open(coverPath).buffered().let { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
                Hotel(
                    hotelId = item.getInt("hotel_id"),
                    hotelName = item.getString("hotel_name"),
                    hotelRating = item.getDouble("hotel_rating").toFloat(),
                    hotelToSkiDistance = item.getDouble("hotel_to_ski_distance").toFloat(),
                    hotelCoverImage = bitmap.asImageBitmap()
                )
            })
        }
    }
}