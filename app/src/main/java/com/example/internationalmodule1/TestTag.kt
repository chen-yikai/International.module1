package com.example.internationalmodule1

import com.example.internationalmodule1.models.paymentMethod

object TestTag {
    object Home {
        val hotelList = "home_hotel_list"
        val hotelListBottom = "home_hotel_list_bottom"
        val searchBar = "home_hotel_list_search_bar"
    }

    object Details {
        val screen = "details_screen"
        val guestReviewsTabBtn = "details_reviews_tab_btn"
        val guestReviewsTab = "details_reviews_tab"
        val roomSelectionTabBtn = "room_selection_tab_btn"
        val roomSelectionTab = "room_selection_tab"
        val reviewsList = "reviews_list"
    }

    object Confirm {
        val screen = "confirm_screen"

        val form = "confirm_form"
        val firstName = "First Name"
        val lastName = "Last Name"
        val checkIn = "Check-in date"
        val checkOut = "Check-out date"

        val adults = "adults"
        val children = "children"

        val sightseeing = "Sightseeing"
        val business = "Business"

        val cash = paymentMethod[0]
        val creditCard = paymentMethod[1]
        val ePay = paymentMethod[2]

        val bookNowBtn = "booknow_btn"

        val errorDialog = "error_dialog"
        val errorDialogYes = "error_dialog_yes"

        val confirmDialog = "confirm_dialog"
        val confirmDialogYes = "confirm_dialog_yes"
    }

    object MyBooking {
        val screen = "booking_screen"
    }
}