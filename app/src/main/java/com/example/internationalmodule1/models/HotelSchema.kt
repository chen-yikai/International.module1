package com.example.internationalmodule1.models

import androidx.compose.ui.graphics.ImageBitmap

data class Hotel(
    val hotelId: Int,
    val hotelName: String,
    val hotelRating: Float,
    val hotelToSkiDistance: Float,
    val hotelCoverImage: ImageBitmap
)

data class HotelDetails(
    val hotelId: Int, val hotelName: String, val guestReviews: GuestReviews, val rooms: List<Room>
)

data class GuestReviews(
    val ratingsCategories: Map<String, Float>, val reviewsObjects: List<ReviewsObject>
)

data class ReviewsObject(
    val username: String, val country: String, val reviewText: String
)

data class Room(
    val roomId: Int,
    val roomType: String,
    val roomBedType: String,
    val roomTotalNumberOfGuests: Int,
    val roomFeatures: List<String>,
    val roomPriceForOneNight: Int
)

data class MyBooking(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val checkIn: Long,
    val checkOut: Long,
    val adultsCount: Int,
    val childrenCount: Int,
    val roomsCount: Int,
    val isBusiness: Boolean,
    val paymentMethod: Int,
    val price: Int
)

val paymentMethod = listOf("Cash", "Credit Card", "E-Pay")
